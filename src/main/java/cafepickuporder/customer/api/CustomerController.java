package cafepickuporder.customer.api;

import cafepickuporder.customer.application.CustomerService;
import cafepickuporder.customer.dto.request.CustomerPasswordUpdateRequest;
import cafepickuporder.customer.dto.request.CustomerPhoneUpdateRequest;
import cafepickuporder.customer.dto.request.CustomerProfileUpdateRequest;
import cafepickuporder.customer.dto.response.CustomerProfileResponse;
import cafepickuporder.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/api/customers/me")
    public ResponseEntity<CustomerProfileResponse> me(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                customerService.getMyProfile(userDetails.getCustomerId())
        );
    }

    @PatchMapping("/api/customers/me/profile")
    public ResponseEntity<CustomerProfileResponse> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CustomerProfileUpdateRequest request
    ) {
        return ResponseEntity.ok(
                customerService.updateProfile(userDetails.getCustomerId(), request)
        );
    }

    @PatchMapping("/api/customers/me/phone")
    public ResponseEntity<CustomerProfileResponse> updatePhone(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CustomerPhoneUpdateRequest request
    ) {
        return ResponseEntity.ok(
                customerService.updatePhone(userDetails.getCustomerId(), request)
        );
    }

    @PatchMapping("/api/customers/me/password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CustomerPasswordUpdateRequest request
    ) {
        customerService.updatePassword(userDetails.getCustomerId(), request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/customers/me/profile-image")
    public ResponseEntity<CustomerProfileResponse> updateProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseEntity.ok(
                customerService.updateProfileImage(userDetails.getCustomerId(), image)
        );
    }
}