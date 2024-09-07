package summer.camp.spring_axon_demo.common_api.dtos;

import lombok.*;

@AllArgsConstructor@NoArgsConstructor@Builder@Getter@Setter@ToString
public class CreateAccountRequestDto {
private String currency;
private double initialBalance;
}
