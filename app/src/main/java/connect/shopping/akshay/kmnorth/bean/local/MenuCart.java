package connect.shopping.akshay.kmnorth.bean.local;

import com.orm.SugarRecord;

import connect.shopping.akshay.kmnorth.bean.other.MenuItem;

/**
 * Created by Akshay on 17-07-2017.
 */

public class MenuCart extends SugarRecord {


     MenuItem menuitem;
     float menu_qty;
     int menu_price;
     int menu_choice;
     String choiceName;
    int menu_id;


    public MenuCart() {
    }

    public MenuCart(int menu_id,String choiceName, int menu_choice, int menu_price, float menu_qty, MenuItem menuitem) {
        this.menu_id = menu_id;
        this.choiceName = choiceName;
        this.menu_choice = menu_choice;
        this.menu_price = menu_price;
        this.menu_qty = menu_qty;
        this.menuitem = menuitem;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getChoiceName() {
        return choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public int getMenu_choice() {
        return menu_choice;
    }

    public void setMenu_choice(int menu_choice) {
        this.menu_choice = menu_choice;
    }

    public int getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(int menu_price) {
        this.menu_price = menu_price;
    }

    public float getMenu_qty() {
        return menu_qty;
    }

    public void setMenu_qty(float menu_qty) {
        this.menu_qty = menu_qty;
    }

    public MenuItem getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(MenuItem menuitem) {
        this.menuitem = menuitem;
    }
}
