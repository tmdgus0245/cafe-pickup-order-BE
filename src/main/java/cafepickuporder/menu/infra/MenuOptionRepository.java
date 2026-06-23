package cafepickuporder.menu.infra;

import cafepickuporder.menu.domain.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    List<MenuOption> findByOptionGroupIdOrderByDisplayOrderAsc(Long optionGroupId);

    void deleteByOptionGroupId(Long optionGroupId);
}
