package connect.shopping.akshay.kmnorth.bean.response;

import java.util.List;

import connect.shopping.akshay.kmnorth.bean.other.UserPromo;

/**
 * Created by Akshay on 24-07-2017.
 */
public class UsersPromoCodeResponse {

    private List<UserPromo> promocodes;

    public List<UserPromo> getPromocodes() {
        return promocodes;
    }

    public void setPromocodes(List<UserPromo> promocodes) {
        this.promocodes = promocodes;
    }
}
