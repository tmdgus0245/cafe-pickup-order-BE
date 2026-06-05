package cafepickuporder.menu.dto.response;

import cafepickuporder.menu.domain.MenuOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionResponse {

    private Long optionId;
    private String name;
    private Integer additionalPrice;
    private Integer displayOrder;

    public static MenuOptionResponse from(MenuOption option) {
        return new MenuOptionResponse(
                option.getId(),
                option.getName(),
                option.getAdditionalPrice(),
                option.getDisplayOrder()
        );
    }
}