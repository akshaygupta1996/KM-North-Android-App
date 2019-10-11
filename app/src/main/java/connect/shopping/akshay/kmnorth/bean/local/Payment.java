package connect.shopping.akshay.kmnorth.bean.local;

import java.io.Serializable;

/**
 * Created by Akshay on 26-07-2017.
 */

public class Payment implements Serializable {

    private int id;
    private String transaction_id;
    private String date_time_of_payment;
    private String payment_type;
    private int amount;
    private int amount_discount;
    private int amount_tax;
    private int amount_wallet;
    private int amount_menu;
    private int amount_payable;

    public Payment() {
    }

    public Payment(int amount, int amount_discount, int amount_menu, int amount_payable, int amount_tax, int amount_wallet, String date_time_of_payment, int id, String payment_type, String transaction_id) {
        this.amount = amount;
        this.amount_discount = amount_discount;
        this.amount_menu = amount_menu;
        this.amount_payable = amount_payable;
        this.amount_tax = amount_tax;
        this.amount_wallet = amount_wallet;
        this.date_time_of_payment = date_time_of_payment;
        this.id = id;
        this.payment_type = payment_type;
        this.transaction_id = transaction_id;
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

    public String getDate_time_of_payment() {
        return date_time_of_payment;
    }

    public void setDate_time_of_payment(String date_time_of_payment) {
        this.date_time_of_payment = date_time_of_payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
