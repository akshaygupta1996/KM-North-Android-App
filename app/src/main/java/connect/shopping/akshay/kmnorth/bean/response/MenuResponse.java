package connect.shopping.akshay.kmnorth.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Akshay on 16-07-2017.
 */

public class MenuResponse implements Serializable {

    private boolean status;
    private List<ArrayMenuResponse> menu;

    public List<ArrayMenuResponse> getMenu() {
        return menu;
    }

    public void setMenu(List<ArrayMenuResponse> menu) {
        this.menu = menu;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
