package cafepickuporder.menu.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuOptionGroupRequest {

    private String name;
    private Boolean required;
    private Integer minSelect;
    private Integer maxSelect;
}
