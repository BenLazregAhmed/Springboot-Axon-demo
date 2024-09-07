package summer.camp.spring_axon_demo.queries.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import summer.camp.spring_axon_demo.common_api.enums.TransactionType;

import java.time.Instant;
@AllArgsConstructor@NoArgsConstructor@Getter@Setter
@Builder
@Entity
public class AccountTransaction {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant timeStamp;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
