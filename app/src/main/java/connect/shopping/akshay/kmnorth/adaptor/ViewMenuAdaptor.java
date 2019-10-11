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
 * Created by Akshay on 29-07-2017.
 */

public class ViewMenuAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<MenuItemOrder> menuList;

    public ViewMenuAdaptor(Activity activity, List<MenuItemOrder> menuList) {
        this.activity = activity;
        this.menuList = menuList;


    }
    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int i) {
        return menuList.get(i);
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
        TextView txtDescription = (TextView)convertView.findViewById(R.id.txtCartMenuDescription);

        MenuItemOrder menuItemOrder = menuList.get(i);

        txtCartMenuName.setText(menuItemOrder.getMenu_name());
        txtCartMenuPrice.setText("\u20B9 " + menuItemOrder.getMenu_amount());
        txtCartMenuQty.setText(menuItemOrder.getMenu_qty()+"");
        txtDescription.setText(menuItemOrder.getMenu_description());

        return convertView;
    }
}
