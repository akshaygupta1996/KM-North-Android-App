package connect.shopping.akshay.kmnorth.bean.local;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Akshay on 03-08-2017.
 */

public class FirebaseOrder implements Serializable {

    private Address address;
    private String datetime;
    private ArrayList<MenuItemOrder> menu;
    private Order order;
    private Payment payment;
    private String status;
    private User user;
    private int user_id;

    public FirebaseOrder() {
    }

    public FirebaseOrder(Address address, String datetime, ArrayList<MenuItemOrder> menu, Order order, Payment payment, String status, User user, int user_id) {
        this.address = address;
        this.datetime = datetime;
        this.menu = menu;
        this.order = order;
        this.payment = payment;
        this.status = status;
        this.user = user;
        this.user_id = user_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public ArrayList<MenuItemOrder> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuItemOrder> menu) {
        this.menu = menu;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
