package cafepickuporder.customer.application;

import cafepickuporder.customer.domain.Customer;
import cafepickuporder.customer.domain.CustomerFavoriteStore;
import cafepickuporder.customer.infra.CustomerFavoriteStoreRepository;
import cafepickuporder.customer.infra.CustomerRepository;
import cafepickuporder.store.domain.Store;
import cafepickuporder.store.dto.response.StoreListResponse;
import cafepickuporder.store.infra.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerFavoriteStoreService {

    private final CustomerFavoriteStoreRepository favoriteStoreRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    public void addFavorite(Long customerId, Long storeId) {
        if (favoriteStoreRepository
                .existsByCustomerIdAndStoreId(customerId, storeId)) {
            return;
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("고객을 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("매장을 찾을 수 없습니다."));

        favoriteStoreRepository.save(
                new CustomerFavoriteStore(customer, store)
        );
    }

    public void removeFavorite(Long customerId, Long storeId) {
        CustomerFavoriteStore favorite =
                favoriteStoreRepository
                        .findByCustomerIdAndStoreId(customerId, storeId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "즐겨찾기한 매장이 아닙니다."
                                ));

        favoriteStoreRepository.delete(favorite);
    }

    @Transactional(readOnly = true)
    public List<StoreListResponse> getFavoriteStores(Long customerId) {
        return favoriteStoreRepository
                .findAllByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(CustomerFavoriteStore::getStore)
                .map(StoreListResponse::from)
                .toList();
    }
}