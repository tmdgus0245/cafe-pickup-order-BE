package cafepickuporder.auth.application;


import cafepickuporder.auth.dto.CustomerSignupRequest;
import cafepickuporder.auth.dto.CustomerSignupResponse;
import cafepickuporder.customer.domain.Customer;
import cafepickuporder.customer.infra.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerSignupResponse signup(CustomerSignupRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Customer customer = Customer.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerSignupResponse.from(savedCustomer);
    }
}