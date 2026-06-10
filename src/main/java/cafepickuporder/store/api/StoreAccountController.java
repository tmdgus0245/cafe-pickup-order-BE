package cafepickuporder.store.api;

import cafepickuporder.store.application.StoreAccountService;
import cafepickuporder.store.dto.request.StoreAccountLoginRequest;
import cafepickuporder.store.dto.request.StoreAccountSignupRequest;
import cafepickuporder.store.dto.response.StoreAccountLoginResponse;
import cafepickuporder.store.dto.response.StoreAccountSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}