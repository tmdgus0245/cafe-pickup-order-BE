package cafepickuporder.menu.dto.request;

import cafepickuporder.menu.domain.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuManageRequest {

    private Long categoryId;
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
    private MenuStatus status;
    private Integer displayOrder;
}
