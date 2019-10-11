package connect.shopping.akshay.kmnorth.bean.other;

import java.io.Serializable;

/**
 * Created by Akshay on 15-07-2017.
 */
public class MainCategory implements Serializable{

    private int uniqueId;
    private String cat_name;

    public MainCategory(String cat_name, int uniqueId) {
        this.cat_name = cat_name;
        this.uniqueId = uniqueId;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
}