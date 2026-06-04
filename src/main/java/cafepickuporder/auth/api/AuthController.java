package cafepickuporder.auth.api;

import cafepickuporder.auth.application.AuthService;
import cafepickuporder.auth.dto.CustomerSignupRequest;
import cafepickuporder.auth.dto.CustomerSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CustomerSignupResponse> signup(
            @RequestBody CustomerSignupRequest request
    ) {
        return ResponseEntity.ok(authService.signup(request));
    }
}