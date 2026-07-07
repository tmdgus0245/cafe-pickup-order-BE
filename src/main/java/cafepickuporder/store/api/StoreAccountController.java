package cafepickuporder.store.api;

import cafepickuporder.global.security.StoreAccountPrincipal;
import cafepickuporder.store.application.StoreAccountService;
import cafepickuporder.store.dto.request.StoreAccountLoginRequest;
import cafepickuporder.store.dto.request.StoreAccountSignupRequest;
import cafepickuporder.store.dto.response.StoreAccountLoginResponse;
import cafepickuporder.store.dto.response.StoreAccountSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-accounts")
@RequiredArgsConstructor
public class StoreAccountController {

    private final StoreAccountService storeAccountService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public StoreAccountSignupResponse signup(
            @RequestBody StoreAccountSignupRequest request
    ) {
        return storeAccountService.signup(request);
    }

    @PostMapping("/login")
    public StoreAccountLoginResponse login(
            @RequestBody StoreAccountLoginRequest request
    ) {
        return storeAccountService.login(request);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw(
            @AuthenticationPrincipal StoreAccountPrincipal principal
    ) {
        storeAccountService.withdraw(principal.getStoreAccountId());
        return ResponseEntity.noContent().build();
    }
}