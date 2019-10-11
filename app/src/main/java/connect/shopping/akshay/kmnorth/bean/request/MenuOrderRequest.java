package connect.shopping.akshay.kmnorth.bean.request;

/**
 * Created by Akshay on 25-07-2017.
 */

public class  MenuOrderRequest {

    private int user_id;
    private int address_id;
    private String promo_code;
    private String special_note_required;
    private String payment_type;
    private int amount;
    private int amount_payable;
    private int amount_tax;
    private int amount_discount;
    private int amount_wallet;
    private int amount_menu;
    private String menu;


    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_discount() {
        return amount_discount;
    }

    public void setAmount_discount(int amount_discount) {
        this.amount_discount = amount_discount;
    }

    public int getAmount_menu() {
        return amount_menu;
    }

    public void setAmount_menu(int amount_menu) {
        this.amount_menu = amount_menu;
    }

    public int getAmount_payable() {
        return amount_payable;
    }

    public void setAmount_payable(int amount_payable) {
        this.amount_payable = amount_payable;
    }

    public int getAmount_tax() {
        return amount_tax;
    }

    public void setAmount_tax(int amount_tax) {
        this.amount_tax = amount_tax;
    }

    public int getAmount_wallet() {
        return amount_wallet;
    }

    public void setAmount_wallet(int amount_wallet) {
        this.amount_wallet = amount_wallet;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getSpecial_note_required() {
        return special_note_required;
    }

    public void setSpecial_note_required(String special_note_required) {
        this.special_note_required = special_note_required;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
