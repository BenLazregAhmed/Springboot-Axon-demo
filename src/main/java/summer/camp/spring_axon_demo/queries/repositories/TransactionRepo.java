package summer.camp.spring_axon_demo.queries.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import summer.camp.spring_axon_demo.queries.entities.Account;
import summer.camp.spring_axon_demo.queries.entities.AccountTransaction;

public interface TransactionRepo extends JpaRepository<AccountTransaction,String> {
}
