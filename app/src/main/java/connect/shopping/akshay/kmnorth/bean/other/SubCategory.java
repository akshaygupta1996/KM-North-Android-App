package connect.shopping.akshay.kmnorth.bean.other;

import java.io.Serializable;

/**
 * Created by Akshay on 16-07-2017.
 */

public class SubCategory implements Serializable{

    private int main_cat_id;
    private int uniqueId;
    private String cat_name;

    public SubCategory(String cat_name, int main_cat_id, int uniqueId) {
        this.cat_name = cat_name;
        this.main_cat_id = main_cat_id;
        this.uniqueId = uniqueId;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getMain_cat_id() {
        return main_cat_id;
    }

    public void setMain_cat_id(int main_cat_id) {
        this.main_cat_id = main_cat_id;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
}
