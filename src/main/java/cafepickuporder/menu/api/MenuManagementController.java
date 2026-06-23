package cafepickuporder.menu.api;

import cafepickuporder.global.security.StoreAccountPrincipal;
import cafepickuporder.menu.application.MenuManagementService;
import cafepickuporder.menu.dto.request.MenuCategoryRequest;
import cafepickuporder.menu.dto.request.MenuManageRequest;
import cafepickuporder.menu.dto.request.MenuOptionGroupRequest;
import cafepickuporder.menu.dto.request.MenuOptionRequest;
import cafepickuporder.menu.dto.response.MenuCategoryResponse;
import cafepickuporder.menu.dto.response.MenuDetailResponse;
import cafepickuporder.menu.dto.response.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores/{storeId}/manage")
@RequiredArgsConstructor
public class MenuManagementController {

    private final MenuManagementService menuManagementService;

    @GetMapping("/categories")
    public List<MenuCategoryResponse> getCategories(
            @PathVariable Long storeId,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.getCategories(storeId);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuCategoryResponse createCategory(
            @PathVariable Long storeId,
            @RequestBody MenuCategoryRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.createCategory(storeId, request);
    }

    @PatchMapping("/categories/{categoryId}")
    public MenuCategoryResponse updateCategory(
            @PathVariable Long storeId,
            @PathVariable Long categoryId,
            @RequestBody MenuCategoryRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.updateCategory(storeId, categoryId, request);
    }

    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable Long storeId,
            @PathVariable Long categoryId,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        menuManagementService.deleteCategory(storeId, categoryId);
    }

    @PostMapping("/menus")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponse createMenu(
            @PathVariable Long storeId,
            @RequestBody MenuManageRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.createMenu(storeId, request);
    }

    @PatchMapping("/menus/{menuId}")
    public MenuResponse updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuManageRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.updateMenu(storeId, menuId, request);
    }

    @DeleteMapping("/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        menuManagementService.deleteMenu(storeId, menuId);
    }

    @PostMapping("/menus/{menuId}/option-groups")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDetailResponse createOptionGroup(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuOptionGroupRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.createOptionGroup(storeId, menuId, request);
    }

    @PatchMapping("/menus/{menuId}/option-groups/{optionGroupId}")
    public MenuDetailResponse updateOptionGroup(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @PathVariable Long optionGroupId,
            @RequestBody MenuOptionGroupRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.updateOptionGroup(storeId, menuId, optionGroupId, request);
    }

    @DeleteMapping("/menus/{menuId}/option-groups/{optionGroupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroup(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @PathVariable Long optionGroupId,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        menuManagementService.deleteOptionGroup(storeId, menuId, optionGroupId);
    }

    @PostMapping("/menus/{menuId}/option-groups/{optionGroupId}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDetailResponse createOption(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @PathVariable Long optionGroupId,
            @RequestBody MenuOptionRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.createOption(storeId, menuId, optionGroupId, request);
    }

    @PatchMapping("/menus/{menuId}/option-groups/{optionGroupId}/options/{optionId}")
    public MenuDetailResponse updateOption(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @PathVariable Long optionGroupId,
            @PathVariable Long optionId,
            @RequestBody MenuOptionRequest request,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        return menuManagementService.updateOption(storeId, menuId, optionGroupId, optionId, request);
    }

    @DeleteMapping("/menus/{menuId}/option-groups/{optionGroupId}/options/{optionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @PathVariable Long optionGroupId,
            @PathVariable Long optionId,
            Authentication authentication
    ) {
        validateStoreAccess(storeId, authentication);

        menuManagementService.deleteOption(storeId, menuId, optionGroupId, optionId);
    }

    private void validateStoreAccess(Long storeId, Authentication authentication) {
        StoreAccountPrincipal principal =
                (StoreAccountPrincipal) authentication.getPrincipal();

        if (!principal.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("해당 매장의 메뉴를 관리할 권한이 없습니다.");
        }
    }
}
