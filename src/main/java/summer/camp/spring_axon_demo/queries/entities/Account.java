package summer.camp.spring_axon_demo.queries.entities;

import jakarta.persistence.*;
import lombok.*;
import summer.camp.spring_axon_demo.common_api.enums.ACCOUNT_STATUS;

import java.time.Instant;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Account {
    @Id
    private String id;
    private Instant createdAt;
    private double balance;
    @Enumerated(EnumType.STRING)
    private ACCOUNT_STATUS accountStatus;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction>transactions;
}
