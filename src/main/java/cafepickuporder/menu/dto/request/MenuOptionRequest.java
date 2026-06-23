package cafepickuporder.menu.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuOptionRequest {

    private String name;
    private Integer additionalPrice;
    private Integer displayOrder;
}
