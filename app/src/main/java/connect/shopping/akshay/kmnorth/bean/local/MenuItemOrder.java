package connect.shopping.akshay.kmnorth.bean.local;

import java.io.Serializable;

/**
 * Created by Akshay on 26-07-2017.
 */

public class MenuItemOrder implements Serializable {

    private int menu_id;
    private int menu_choice;
    private float menu_qty;
    private int menu_amount;
    private String menu_choice_one;
    private String menu_choice_two;
    private String menu_name;
    private String menu_description;

    public MenuItemOrder() {
    }

    public MenuItemOrder(int menu_amount, int menu_choice, String menu_choice_one, String menu_choice_two, String menu_description, int menu_id, String menu_name, float menu_qty) {
        this.menu_amount = menu_amount;
        this.menu_choice = menu_choice;
        this.menu_choice_one = menu_choice_one;
        this.menu_choice_two = menu_choice_two;
        this.menu_description = menu_description;
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.menu_qty = menu_qty;
    }

    public String getMenu_choice_one() {
        return menu_choice_one;
    }

    public void setMenu_choice_one(String menu_choice_one) {
        this.menu_choice_one = menu_choice_one;
    }

    public String getMenu_choice_two() {
        return menu_choice_two;
    }

    public void setMenu_choice_two(String menu_choice_two) {
        this.menu_choice_two = menu_choice_two;
    }

    public String getMenu_description() {
        return menu_description;
    }

    public void setMenu_description(String menu_description) {
        this.menu_description = menu_description;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getMenu_amount() {
        return menu_amount;
    }

    public void setMenu_amount(int menu_amount) {
        this.menu_amount = menu_amount;
    }

    public int getMenu_choice() {
        return menu_choice;
    }

    public void setMenu_choice(int menu_choice) {
        this.menu_choice = menu_choice;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public float getMenu_qty() {
        return menu_qty;
    }

    public void setMenu_qty(float menu_qty) {
        this.menu_qty = menu_qty;
    }
}
