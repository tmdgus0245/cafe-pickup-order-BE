package cafepickuporder.menu.infra;

import cafepickuporder.menu.domain.MenuOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionGroupRepository extends JpaRepository<MenuOptionGroup, Long> {

    List<MenuOptionGroup> findByMenuId(Long menuId);

    void deleteByMenuId(Long menuId);
}
