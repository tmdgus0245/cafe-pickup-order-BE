package cafepickuporder.menu.api;

import cafepickuporder.menu.application.MenuQueryService;
import cafepickuporder.menu.dto.response.MenuDetailResponse;
import cafepickuporder.menu.dto.response.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuQueryService menuQueryService;

    @GetMapping("/api/stores/{storeId}/menus")
    public ResponseEntity<List<MenuResponse>> getMenusByStore(
            @PathVariable Long storeId
    ) {
        return ResponseEntity.ok(menuQueryService.getMenusByStore(storeId));
    }

    @GetMapping("/api/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuDetailResponse> getMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ) {
        return ResponseEntity.ok(menuQueryService.getMenu(storeId, menuId));
    }
}