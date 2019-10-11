package connect.shopping.akshay.kmnorth.bean.response;

import java.util.List;

/**
 * Created by Akshay on 29-07-2017.
 */

public class OrderHistoryResponse {

    private boolean status;
    private List<MenuOrderResponse> menu;

    public List<MenuOrderResponse> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuOrderResponse> menu) {
        this.menu = menu;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
