package cafepickuporder.menu.dto.response;

import cafepickuporder.menu.domain.Menu;
import cafepickuporder.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponse {

    private Long menuId;
    private Long categoryId;
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
    private MenuStatus status;
    private Integer displayOrder;

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getCategory().getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getImageUrl(),
                menu.getStatus(),
                menu.getDisplayOrder()
        );
    }
}