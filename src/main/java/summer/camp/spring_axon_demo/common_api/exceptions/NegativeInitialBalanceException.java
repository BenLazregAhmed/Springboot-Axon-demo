package summer.camp.spring_axon_demo.common_api.exceptions;

public class NegativeInitialBalanceException extends RuntimeException {
    public NegativeInitialBalanceException(String m)
    {
        super(m);
    }
}
