package connect.shopping.akshay.kmnorth.bean.response;

import java.io.Serializable;
import java.util.List;

import connect.shopping.akshay.kmnorth.bean.other.MenuItem;
import connect.shopping.akshay.kmnorth.bean.other.SubCategory;

/**
 * Created by Akshay on 16-07-2017.
 */

public class ArrayInsideMainResponse implements Serializable {

    private List<connect.shopping.akshay.kmnorth.bean.other.MenuItem> items;
    private SubCategory subcategory;

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }
}
