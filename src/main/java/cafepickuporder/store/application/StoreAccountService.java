package cafepickuporder.store.application;

import cafepickuporder.global.security.JwtTokenProvider;
import cafepickuporder.store.domain.Store;
import cafepickuporder.store.domain.StoreAccount;
import cafepickuporder.store.dto.request.StoreAccountLoginRequest;
import cafepickuporder.store.dto.request.StoreAccountSignupRequest;
import cafepickuporder.store.dto.response.StoreAccountLoginResponse;
import cafepickuporder.store.dto.response.StoreAccountSignupResponse;
import cafepickuporder.store.infra.StoreAccountRepository;
import cafepickuporder.store.infra.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreAccountService {

    private final StoreAccountRepository storeAccountRepository;
    private final StoreRepository storeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public StoreAccountSignupResponse signup(StoreAccountSignupRequest request) {
        if (storeAccountRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 점주 이메일입니다.");
        }

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        StoreAccount storeAccount = new StoreAccount(
                store,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName()
        );

        StoreAccount savedStoreAccount = storeAccountRepository.save(storeAccount);

        return StoreAccountSignupResponse.from(savedStoreAccount);
    }

    @Transactional(readOnly = true)
    public StoreAccountLoginResponse login(StoreAccountLoginRequest request) {
        StoreAccount storeAccount = storeAccountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("점주 계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), storeAccount.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createStoreAccountToken(
                storeAccount.getStore().getId()
        );

        return StoreAccountLoginResponse.of(storeAccount, token);
    }
}