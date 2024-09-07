package summer.camp.spring_axon_demo.queries.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import summer.camp.spring_axon_demo.queries.entities.Account;

public interface AccountRepo extends JpaRepository<Account,String> {
}
