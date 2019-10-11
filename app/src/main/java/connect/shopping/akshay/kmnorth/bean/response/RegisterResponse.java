package connect.shopping.akshay.kmnorth.bean.response;

/**
 * Created by Akshay on 03-07-2017.
 */

public class RegisterResponse {

    private boolean status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
