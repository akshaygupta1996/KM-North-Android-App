package connect.shopping.akshay.kmnorth.bean.other;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Akshay on 16-07-2017.
 */


public class  MenuItem extends SugarRecord implements Serializable {


    private String name;
    private String description;
    private int cat_id;
    private int full_price;
    private int half_price;
    private int uniqueId;
    private boolean choice;
    private String choice_one;
    private String choice_two;

    public MenuItem() {
    }

    public MenuItem(int cat_id, boolean choice, String choice_one, String choice_two, String description, int full_price, int half_price, String name, int uniqueId) {
        this.cat_id = cat_id;
        this.choice = choice;
        this.choice_one = choice_one;
        this.choice_two = choice_two;
        this.description = description;
        this.full_price = full_price;
        this.half_price = half_price;
        this.name = name;
        this.uniqueId = uniqueId;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public String getChoice_one() {
        return choice_one;
    }

    public void setChoice_one(String choice_one) {
        this.choice_one = choice_one;
    }

    public String getChoice_two() {
        return choice_two;
    }

    public void setChoice_two(String choice_two) {
        this.choice_two = choice_two;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFull_price() {
        return full_price;
    }

    public void setFull_price(int full_price) {
        this.full_price = full_price;
    }

    public int getHalf_price() {
        return half_price;
    }

    public void setHalf_price(int half_price) {
        this.half_price = half_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
}
