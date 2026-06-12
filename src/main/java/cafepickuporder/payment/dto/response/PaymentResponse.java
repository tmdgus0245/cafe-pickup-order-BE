package cafepickuporder.payment.dto.response;

import cafepickuporder.payment.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private String paymentKey;
    private String method;
    private String status;
    private Integer amount;
    private LocalDateTime paidAt;

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getPaymentKey(),
                payment.getMethod().name(),
                payment.getStatus().name(),
                payment.getAmount(),
                payment.getPaidAt()
        );
    }
}