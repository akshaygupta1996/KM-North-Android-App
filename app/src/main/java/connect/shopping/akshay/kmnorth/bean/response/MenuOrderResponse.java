package connect.shopping.akshay.kmnorth.bean.response;

import java.io.Serializable;
import java.util.List;

import connect.shopping.akshay.kmnorth.bean.local.MenuItemOrder;
import connect.shopping.akshay.kmnorth.bean.local.Order;
import connect.shopping.akshay.kmnorth.bean.local.Payment;

/**
 * Created by Akshay on 26-07-2017.
 */

public class MenuOrderResponse implements Serializable {

    private boolean status;
    private Order order;
    private Payment payment;
    private List<MenuItemOrder> menu;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<MenuItemOrder> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItemOrder> menu) {
        this.menu = menu;
    }
}
