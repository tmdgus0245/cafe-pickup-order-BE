package cafepickuporder.menu.application;

import cafepickuporder.menu.domain.Menu;
import cafepickuporder.menu.domain.MenuCategory;
import cafepickuporder.menu.domain.MenuOption;
import cafepickuporder.menu.domain.MenuOptionGroup;
import cafepickuporder.menu.domain.MenuStatus;
import cafepickuporder.menu.dto.request.MenuCategoryRequest;
import cafepickuporder.menu.dto.request.MenuManageRequest;
import cafepickuporder.menu.dto.request.MenuOptionGroupRequest;
import cafepickuporder.menu.dto.request.MenuOptionRequest;
import cafepickuporder.menu.dto.response.MenuCategoryResponse;
import cafepickuporder.menu.dto.response.MenuDetailResponse;
import cafepickuporder.menu.dto.response.MenuOptionGroupResponse;
import cafepickuporder.menu.dto.response.MenuOptionResponse;
import cafepickuporder.menu.dto.response.MenuResponse;
import cafepickuporder.menu.infra.MenuCategoryRepository;
import cafepickuporder.menu.infra.MenuOptionGroupRepository;
import cafepickuporder.menu.infra.MenuOptionRepository;
import cafepickuporder.menu.infra.MenuRepository;
import cafepickuporder.store.domain.Store;
import cafepickuporder.store.infra.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuManagementService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;

    @Transactional(readOnly = true)
    public List<MenuCategoryResponse> getCategories(Long storeId) {
        return menuCategoryRepository.findByStoreIdOrderByDisplayOrderAsc(storeId)
                .stream()
                .map(MenuCategoryResponse::from)
                .toList();
    }

    public MenuCategoryResponse createCategory(Long storeId, MenuCategoryRequest request) {
        Store store = getStore(storeId);

        MenuCategory category = MenuCategory.builder()
                .store(store)
                .name(request.getName())
                .displayOrder(request.getDisplayOrder())
                .build();

        return MenuCategoryResponse.from(menuCategoryRepository.save(category));
    }

    public MenuCategoryResponse updateCategory(Long storeId, Long categoryId, MenuCategoryRequest request) {
        MenuCategory category = getStoreCategory(storeId, categoryId);
        category.update(request.getName(), request.getDisplayOrder());

        return MenuCategoryResponse.from(category);
    }

    public void deleteCategory(Long storeId, Long categoryId) {
        MenuCategory category = getStoreCategory(storeId, categoryId);

        if (!menuRepository.findByCategoryIdOrderByDisplayOrderAsc(category.getId()).isEmpty()) {
            throw new IllegalStateException("카테고리에 메뉴가 남아 있어 삭제할 수 없습니다.");
        }

        menuCategoryRepository.delete(category);
    }

    public MenuResponse createMenu(Long storeId, MenuManageRequest request) {
        Store store = getStore(storeId);
        MenuCategory category = getStoreCategory(storeId, request.getCategoryId());

        Menu menu = Menu.builder()
                .store(store)
                .category(category)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .status(request.getStatus() == null ? MenuStatus.ON_SALE : request.getStatus())
                .displayOrder(request.getDisplayOrder())
                .build();

        return MenuResponse.from(menuRepository.save(menu));
    }

    public MenuResponse updateMenu(Long storeId, Long menuId, MenuManageRequest request) {
        Menu menu = getStoreMenu(storeId, menuId);
        MenuCategory category = getStoreCategory(storeId, request.getCategoryId());

        menu.update(
                category,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getImageUrl(),
                request.getStatus() == null ? MenuStatus.ON_SALE : request.getStatus(),
                request.getDisplayOrder()
        );

        return MenuResponse.from(menu);
    }

    public void deleteMenu(Long storeId, Long menuId) {
        Menu menu = getStoreMenu(storeId, menuId);
        List<MenuOptionGroup> optionGroups = menuOptionGroupRepository.findByMenuId(menu.getId());

        optionGroups.forEach(group -> menuOptionRepository.deleteByOptionGroupId(group.getId()));
        menuOptionGroupRepository.deleteByMenuId(menu.getId());
        menuRepository.delete(menu);
    }

    public MenuDetailResponse createOptionGroup(Long storeId, Long menuId, MenuOptionGroupRequest request) {
        Menu menu = getStoreMenu(storeId, menuId);

        MenuOptionGroup optionGroup = MenuOptionGroup.builder()
                .menu(menu)
                .name(request.getName())
                .required(request.getRequired() != null && request.getRequired())
                .minSelect(request.getMinSelect())
                .maxSelect(request.getMaxSelect())
                .build();

        menuOptionGroupRepository.save(optionGroup);

        return getMenuDetail(menu);
    }

    public MenuDetailResponse updateOptionGroup(
            Long storeId,
            Long menuId,
            Long optionGroupId,
            MenuOptionGroupRequest request
    ) {
        Menu menu = getStoreMenu(storeId, menuId);
        MenuOptionGroup optionGroup = getMenuOptionGroup(menu.getId(), optionGroupId);

        optionGroup.update(
                request.getName(),
                request.getRequired() != null && request.getRequired(),
                request.getMinSelect(),
                request.getMaxSelect()
        );

        return getMenuDetail(menu);
    }

    public void deleteOptionGroup(Long storeId, Long menuId, Long optionGroupId) {
        Menu menu = getStoreMenu(storeId, menuId);
        MenuOptionGroup optionGroup = getMenuOptionGroup(menu.getId(), optionGroupId);

        menuOptionRepository.deleteByOptionGroupId(optionGroup.getId());
        menuOptionGroupRepository.delete(optionGroup);
    }

    public MenuDetailResponse createOption(
            Long storeId,
            Long menuId,
            Long optionGroupId,
            MenuOptionRequest request
    ) {
        Menu menu = getStoreMenu(storeId, menuId);
        MenuOptionGroup optionGroup = getMenuOptionGroup(menu.getId(), optionGroupId);

        MenuOption option = MenuOption.builder()
                .optionGroup(optionGroup)
                .name(request.getName())
                .additionalPrice(request.getAdditionalPrice() == null ? 0 : request.getAdditionalPrice())
                .displayOrder(request.getDisplayOrder())
                .build();

        menuOptionRepository.save(option);

        return getMenuDetail(menu);
    }

    public MenuDetailResponse updateOption(
            Long storeId,
            Long menuId,
            Long optionGroupId,
            Long optionId,
            MenuOptionRequest request
    ) {
        Menu menu = getStoreMenu(storeId, menuId);
        getMenuOptionGroup(menu.getId(), optionGroupId);
        MenuOption option = getGroupOption(optionGroupId, optionId);

        option.update(
                request.getName(),
                request.getAdditionalPrice() == null ? 0 : request.getAdditionalPrice(),
                request.getDisplayOrder()
        );

        return getMenuDetail(menu);
    }

    public void deleteOption(Long storeId, Long menuId, Long optionGroupId, Long optionId) {
        Menu menu = getStoreMenu(storeId, menuId);
        getMenuOptionGroup(menu.getId(), optionGroupId);
        MenuOption option = getGroupOption(optionGroupId, optionId);

        menuOptionRepository.delete(option);
    }

    private Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));
    }

    private MenuCategory getStoreCategory(Long storeId, Long categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        if (!category.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("해당 매장의 카테고리가 아닙니다.");
        }

        return category;
    }

    private Menu getStoreMenu(Long storeId, Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

        if (!menu.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("해당 매장의 메뉴가 아닙니다.");
        }

        return menu;
    }

    private MenuOptionGroup getMenuOptionGroup(Long menuId, Long optionGroupId) {
        MenuOptionGroup optionGroup = menuOptionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new IllegalArgumentException("옵션 그룹을 찾을 수 없습니다."));

        if (!optionGroup.getMenu().getId().equals(menuId)) {
            throw new IllegalArgumentException("해당 메뉴의 옵션 그룹이 아닙니다.");
        }

        return optionGroup;
    }

    private MenuOption getGroupOption(Long optionGroupId, Long optionId) {
        MenuOption option = menuOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

        if (!option.getOptionGroup().getId().equals(optionGroupId)) {
            throw new IllegalArgumentException("해당 옵션 그룹의 옵션이 아닙니다.");
        }

        return option;
    }

    private MenuDetailResponse getMenuDetail(Menu menu) {
        List<MenuOptionGroupResponse> optionGroups =
                menuOptionGroupRepository.findByMenuId(menu.getId())
                        .stream()
                        .map(this::toOptionGroupResponse)
                        .toList();

        return MenuDetailResponse.of(menu, optionGroups);
    }

    private MenuOptionGroupResponse toOptionGroupResponse(MenuOptionGroup group) {
        List<MenuOptionResponse> options =
                menuOptionRepository.findByOptionGroupIdOrderByDisplayOrderAsc(group.getId())
                        .stream()
                        .map(MenuOptionResponse::from)
                        .toList();

        return MenuOptionGroupResponse.of(group, options);
    }
}
