package connect.shopping.akshay.kmnorth.bean.response;

import java.io.Serializable;
import java.util.List;

import connect.shopping.akshay.kmnorth.bean.other.MainCategory;

/**
 * Created by Akshay on 16-07-2017.
 */

public class ArrayMenuResponse implements Serializable {

    private MainCategory maincategory;
    private List<ArrayInsideMainResponse> allitems;

    public List<ArrayInsideMainResponse> getAllitems() {
        return allitems;
    }

    public void setAllitems(List<ArrayInsideMainResponse> allitems) {
        this.allitems = allitems;
    }

    public MainCategory getMaincategory() {
        return maincategory;
    }

    public void setMaincategory(MainCategory maincategory) {
        this.maincategory = maincategory;
    }
}
