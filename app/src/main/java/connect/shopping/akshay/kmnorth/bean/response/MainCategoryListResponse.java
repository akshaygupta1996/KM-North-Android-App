package connect.shopping.akshay.kmnorth.bean.response;

import java.util.List;

import connect.shopping.akshay.kmnorth.bean.other.MainCategory;

/**
 * Created by Akshay on 15-07-2017.
 */
public class MainCategoryListResponse {

    private boolean status;
    private List<MainCategory> category;

    public List<MainCategory> getCategory() {
        return category;
    }

    public void setCategory(List<MainCategory> category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
