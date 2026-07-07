package cafepickuporder.store.infra;

import cafepickuporder.store.domain.StoreAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreAccountRepository extends JpaRepository<StoreAccount, Long> {

    Optional<StoreAccount> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<StoreAccount> findByEmailAndWithdrawnFalse(String email);

    boolean existsByEmailAndWithdrawnFalse(String email);

    long countByStoreIdAndWithdrawnFalse(Long storeId);
}