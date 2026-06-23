package cafepickuporder.menu.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuCategoryRequest {

    private String name;
    private Integer displayOrder;
}
