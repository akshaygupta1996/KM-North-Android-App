package connect.shopping.akshay.kmnorth.bean.response;

import connect.shopping.akshay.kmnorth.bean.local.User;

/**
 * Created by Akshay on 24-08-2017.
 */

public class ChangePhoneNumberResponse {

    private boolean status;
    private User user;


    public ChangePhoneNumberResponse(boolean status, User user) {
        this.status = status;
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
