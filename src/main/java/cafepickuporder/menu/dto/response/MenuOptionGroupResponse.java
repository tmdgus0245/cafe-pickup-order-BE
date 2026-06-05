package cafepickuporder.menu.dto.response;

import cafepickuporder.menu.domain.MenuOptionGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuOptionGroupResponse {

    private Long optionGroupId;
    private String name;
    private Boolean required;
    private Integer minSelect;
    private Integer maxSelect;
    private List<MenuOptionResponse> options;

    public static MenuOptionGroupResponse of(
            MenuOptionGroup group,
            List<MenuOptionResponse> options
    ) {
        return new MenuOptionGroupResponse(
                group.getId(),
                group.getName(),
                group.getRequired(),
                group.getMinSelect(),
                group.getMaxSelect(),
                options
        );
    }
}