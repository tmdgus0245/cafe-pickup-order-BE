package cafepickuporder.customer.infra;

import cafepickuporder.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmailAndWithdrawnFalse(String email);

    Optional<Customer> findByEmailAndWithdrawnFalse(String email);

    boolean existsByEmailAndIdNotAndWithdrawnFalse(String email, Long id);

    boolean existsByPhoneAndIdNotAndWithdrawnFalse(String phone, Long id);
}