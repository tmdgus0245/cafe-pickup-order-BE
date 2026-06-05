package cafepickuporder.menu.dto.response;

import cafepickuporder.menu.domain.Menu;
import cafepickuporder.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuDetailResponse {

    private Long menuId;
    private Long storeId;
    private Long categoryId;
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
    private MenuStatus status;
    private List<MenuOptionGroupResponse> optionGroups;

    public static MenuDetailResponse of(
            Menu menu,
            List<MenuOptionGroupResponse> optionGroups
    ) {
        return new MenuDetailResponse(
                menu.getId(),
                menu.getStore().getId(),
                menu.getCategory().getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getImageUrl(),
                menu.getStatus(),
                optionGroups
        );
    }
}