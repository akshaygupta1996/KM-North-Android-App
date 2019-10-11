package connect.shopping.akshay.kmnorth.bean.response;

import java.util.List;

import connect.shopping.akshay.kmnorth.bean.other.MenuItem;

/**
 * Created by Akshay on 18-07-2017.
 */
public class MenuItemListResponse {

    private boolean status;
    private List<MenuItem> items;

    public MenuItemListResponse(List<MenuItem> items, boolean status) {
        this.items = items;
        this.status = status;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
