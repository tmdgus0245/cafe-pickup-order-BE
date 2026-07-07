package cafepickuporder.store.application;

import cafepickuporder.store.domain.Store;
import cafepickuporder.store.domain.StoreStatus;
import cafepickuporder.store.dto.response.StoreDetailResponse;
import cafepickuporder.store.dto.response.StoreListResponse;
import cafepickuporder.store.infra.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryService {

    private final StoreRepository storeRepository;

    public List<StoreListResponse> getStores() {
        return storeRepository.findAll()
                .stream()
                .filter(store -> store.getStatus() != StoreStatus.INACTIVE)
                .map(StoreListResponse::from)
                .toList();
    }

    public StoreDetailResponse getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        if (store.getStatus() == StoreStatus.INACTIVE) {
            throw new IllegalArgumentException("비활성화된 매장입니다.");
        }

        return StoreDetailResponse.from(store);
    }
}