package connect.shopping.akshay.kmnorth.bean.response;

import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;

/**
 * Created by Akshay on 08-07-2017.
 */

public class LoginResponse {

    private boolean status;
    private OAuthDetailsRequestResponse user;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public OAuthDetailsRequestResponse getUser() {
        return user;
    }

    public void setUser(OAuthDetailsRequestResponse user) {
        this.user = user;
    }
}
