package connect.shopping.akshay.kmnorth;

/**
 * Created by Akshay on 18-07-2017.
 */
public class ZeroChildException extends Exception {

    private static final long serialVersionUID = 1L;

    public ZeroChildException() {

    }

    public ZeroChildException(String errorMessage) {
        super(errorMessage);
    }

}