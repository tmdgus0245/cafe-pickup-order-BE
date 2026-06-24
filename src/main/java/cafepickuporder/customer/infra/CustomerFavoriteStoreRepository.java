package cafepickuporder.customer.infra;

import cafepickuporder.customer.domain.CustomerFavoriteStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerFavoriteStoreRepository
        extends JpaRepository<CustomerFavoriteStore, Long> {

    boolean existsByCustomerIdAndStoreId(
            Long customerId,
            Long storeId
    );

    Optional<CustomerFavoriteStore> findByCustomerIdAndStoreId(
            Long customerId,
            Long storeId
    );

    List<CustomerFavoriteStore> findAllByCustomerIdOrderByCreatedAtDesc(
            Long customerId
    );
}