package summer.camp.spring_axon_demo.common_api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class DebitAccountRequestDto {
    private String accountId;

    private String currency;
    private double amount;
}
