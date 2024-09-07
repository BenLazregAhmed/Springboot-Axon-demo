package summer.camp.spring_axon_demo.common_api.events;

import lombok.Getter;
import summer.camp.spring_axon_demo.common_api.enums.ACCOUNT_STATUS;

@Getter
public class AccountActivatedEvent extends BaseEvent<String>{


    private ACCOUNT_STATUS accountStatus;
    public AccountActivatedEvent(String id,ACCOUNT_STATUS status) {
        super(id);
        this.accountStatus=status;
    }
}
