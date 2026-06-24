package cafepickuporder.customer.api;

import cafepickuporder.customer.application.CustomerFavoriteStoreService;
import cafepickuporder.global.security.CustomUserDetails;
import cafepickuporder.store.dto.response.StoreListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/me/favorite-stores")
@RequiredArgsConstructor
public class CustomerFavoriteStoreController {

    private final CustomerFavoriteStoreService favoriteStoreService;

    @PostMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long storeId
    ) {
        favoriteStoreService.addFavorite(
                userDetails.getCustomerId(),
                storeId
        );
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long storeId
    ) {
        favoriteStoreService.removeFavorite(
                userDetails.getCustomerId(),
                storeId
        );
    }

    @GetMapping
    public List<StoreListResponse> getFavoriteStores(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return favoriteStoreService.getFavoriteStores(
                userDetails.getCustomerId()
        );
    }
}