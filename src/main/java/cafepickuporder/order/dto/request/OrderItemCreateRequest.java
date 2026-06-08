package cafepickuporder.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderItemCreateRequest {

    private Long menuId;
    private Integer quantity;
    private List<Long> optionIds;
}