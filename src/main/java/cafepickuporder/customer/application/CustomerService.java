package cafepickuporder.customer.application;

import cafepickuporder.customer.domain.Customer;
import cafepickuporder.customer.dto.request.CustomerPasswordUpdateRequest;
import cafepickuporder.customer.dto.request.CustomerPhoneUpdateRequest;
import cafepickuporder.customer.dto.request.CustomerProfileUpdateRequest;
import cafepickuporder.customer.dto.response.CustomerProfileResponse;
import cafepickuporder.customer.infra.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public CustomerProfileResponse getMyProfile(Long customerId) {
        Customer customer = getCustomer(customerId);
        return CustomerProfileResponse.from(customer);
    }

    public CustomerProfileResponse updateProfile(Long customerId, CustomerProfileUpdateRequest request) {
        Customer customer = getCustomer(customerId);

        if (customerRepository.existsByEmailAndIdNot(request.getEmail(), customerId)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        customer.updateProfile(
                request.getName(),
                request.getEmail(),
                request.getProfileImageUrl()
        );

        return CustomerProfileResponse.from(customer);
    }

    public CustomerProfileResponse updatePhone(Long customerId, CustomerPhoneUpdateRequest request) {
        Customer customer = getCustomer(customerId);

        if (customerRepository.existsByPhoneAndIdNot(request.getPhone(), customerId)) {
            throw new IllegalArgumentException("이미 사용 중인 휴대폰 번호입니다.");
        }

        customer.updatePhone(request.getPhone());

        return CustomerProfileResponse.from(customer);
    }

    public void updatePassword(Long customerId, CustomerPasswordUpdateRequest request) {
        Customer customer = getCustomer(customerId);

        if (!passwordEncoder.matches(request.getCurrentPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        customer.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없습니다."));
    }
}