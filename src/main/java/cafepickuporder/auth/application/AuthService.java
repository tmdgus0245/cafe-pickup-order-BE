package cafepickuporder.auth.application;


import cafepickuporder.auth.dto.request.CustomerSignupRequest;
import cafepickuporder.auth.dto.request.LoginRequest;
import cafepickuporder.auth.dto.response.CustomerSignupResponse;
import cafepickuporder.auth.dto.response.LoginResponse;
import cafepickuporder.customer.domain.Customer;
import cafepickuporder.customer.infra.CustomerRepository;
import cafepickuporder.global.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

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

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(
                customer.getId()
        );

        return LoginResponse.of(customer, accessToken);
    }
}