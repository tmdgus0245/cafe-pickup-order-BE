package cafepickuporder.menu.infra;

import cafepickuporder.menu.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    List<MenuCategory> findByStoreIdOrderByDisplayOrderAsc(Long storeId);
}