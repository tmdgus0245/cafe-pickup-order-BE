package cafepickuporder.store.api;

import cafepickuporder.store.application.StoreQueryService;
import cafepickuporder.store.dto.response.StoreDetailResponse;
import cafepickuporder.store.dto.response.StoreListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreQueryService storeQueryService;

    @GetMapping
    public ResponseEntity<List<StoreListResponse>> getStores() {
        return ResponseEntity.ok(storeQueryService.getStores());
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailResponse> getStore(
            @PathVariable Long storeId
    ) {
        return ResponseEntity.ok(storeQueryService.getStore(storeId));
    }
}