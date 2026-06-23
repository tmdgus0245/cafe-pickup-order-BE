package cafepickuporder.menu.dto.response;

import cafepickuporder.menu.domain.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuCategoryResponse {

    private Long categoryId;
    private String name;
    private Integer displayOrder;

    public static MenuCategoryResponse from(MenuCategory category) {
        return new MenuCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDisplayOrder()
        );
    }
}
