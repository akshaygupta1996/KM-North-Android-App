package connect.shopping.akshay.kmnorth.adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.local.MenuItemOrder;

/**
 * Created by Akshay on 26-07-2017.
 */

public class ViewCartAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<MenuItemOrder> menuList;

    public ViewCartAdaptor(Activity activity, List<MenuItemOrder> menuList) {
        this.activity = activity;
        this.menuList = menuList;


    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = activity.getLayoutInflater().inflate(R.layout.menu_cart_list,viewGroup,false);

        TextView txtCartMenuName = (TextView)convertView.findViewById(R.id.txtCartMenuName);
        TextView txtCartMenuPrice = (TextView)convertView.findViewById(R.id.txtCartMenuPrice);
        TextView txtCartMenuQty = (TextView)convertView.findViewById(R.id.txtCartMenuQty);

        MenuItemOrder menuItemOrder = menuList.get(i);

//        txtCartMenuName.setText(menuList.getMenuitem().getName());
//        txtCartMenuPrice.setText("\u20B9 "+menuCart.getMenu_price()+"");
//        if(menuCart.getMenu_qty()==(int)menuCart.getMenu_qty()){
//            txtCartMenuQty.setText((int)menuCart.getMenu_qty()+"");
//        }else {
//            txtCartMenuQty.setText(menuCart.getMenu_qty() + "");
//        }
        return convertView;
    }
}
