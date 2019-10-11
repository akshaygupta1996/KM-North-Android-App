package connect.shopping.akshay.kmnorth.bean.other;

/**
 * Created by Akshay on 24-07-2017.
 */

public class UserPromo {

    private boolean promo_wallet;
    private int promo_discount_per;
    private String promo_description;
    private String promo_code;
    private String userpromo_validity;

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getPromo_description() {
        return promo_description;
    }

    public void setPromo_description(String promo_description) {
        this.promo_description = promo_description;
    }

    public int getPromo_discount_per() {
        return promo_discount_per;
    }

    public void setPromo_discount_per(int promo_discount_per) {
        this.promo_discount_per = promo_discount_per;
    }

    public boolean isPromo_wallet() {
        return promo_wallet;
    }

    public void setPromo_wallet(boolean promo_wallet) {
        this.promo_wallet = promo_wallet;
    }

    public String getUserpromo_validity() {
        return userpromo_validity;
    }

    public void setUserpromo_validity(String userpromo_validity) {
        this.userpromo_validity = userpromo_validity;
    }
}
