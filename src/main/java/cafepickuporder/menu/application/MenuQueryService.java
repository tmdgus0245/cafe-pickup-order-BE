package cafepickuporder.menu.application;

import cafepickuporder.menu.domain.Menu;
import cafepickuporder.menu.domain.MenuOptionGroup;
import cafepickuporder.menu.dto.response.MenuCategoryResponse;
import cafepickuporder.menu.dto.response.MenuDetailResponse;
import cafepickuporder.menu.dto.response.MenuOptionGroupResponse;
import cafepickuporder.menu.dto.response.MenuOptionResponse;
import cafepickuporder.menu.dto.response.MenuResponse;
import cafepickuporder.menu.infra.MenuCategoryRepository;
import cafepickuporder.menu.infra.MenuOptionGroupRepository;
import cafepickuporder.menu.infra.MenuOptionRepository;
import cafepickuporder.menu.infra.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuQueryService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;

    public List<MenuCategoryResponse> getCategoriesByStore(Long storeId) {
        return menuCategoryRepository.findByStoreIdOrderByDisplayOrderAsc(storeId)
                .stream()
                .map(MenuCategoryResponse::from)
                .toList();
    }

    public List<MenuResponse> getMenusByStore(Long storeId) {
        return menuRepository.findByStoreIdOrderByDisplayOrderAsc(storeId)
                .stream()
                .map(MenuResponse::from)
                .toList();
    }

    public MenuDetailResponse getMenu(Long storeId, Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

        validateMenuBelongsToStore(menu, storeId);

        List<MenuOptionGroupResponse> optionGroups =
                menuOptionGroupRepository.findByMenuId(menuId)
                        .stream()
                        .map(this::toOptionGroupResponse)
                        .toList();

        return MenuDetailResponse.of(menu, optionGroups);
    }

    private void validateMenuBelongsToStore(Menu menu, Long storeId) {
        if (!menu.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("해당 매장의 메뉴가 아닙니다.");
        }
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
