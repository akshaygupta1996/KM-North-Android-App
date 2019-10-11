package connect.shopping.akshay.kmnorth.adaptor;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;

/**
 * Created by Akshay on 17-07-2017.
 */

public class MenuCartAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<MenuCart> menuCartList;

    public MenuCartAdaptor(Activity activity, List<MenuCart> menuCartList) {
        this.activity = activity;
        this.menuCartList = menuCartList;

        Log.d("ADAPTOR", menuCartList.size()+"");

    }
    @Override
    public int getCount() {
        return menuCartList.size();
    }

    @Override
    public Object getItem(int i) {

        return menuCartList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

//        if (inflater == null)
//            inflater = (LayoutInflater) activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (convertView == null)
//            convertView = inflater.inflate(R.layout.menu_cart_list, viewGroup,false);

        convertView = activity.getLayoutInflater().inflate(R.layout.menu_cart_list,viewGroup,false);

        TextView txtCartMenuName = (TextView)convertView.findViewById(R.id.txtCartMenuName);
        TextView txtCartMenuPrice = (TextView)convertView.findViewById(R.id.txtCartMenuPrice);
        TextView txtCartMenuQty = (TextView)convertView.findViewById(R.id.txtCartMenuQty);

        MenuCart menuCart = menuCartList.get(i);

        txtCartMenuName.setText(menuCart.getMenuitem().getName());
        txtCartMenuPrice.setText("\u20B9 "+menuCart.getMenu_price()+"");
        if(menuCart.getMenu_qty()==(int)menuCart.getMenu_qty()){
            txtCartMenuQty.setText((int)menuCart.getMenu_qty()+"");
        }else {
            txtCartMenuQty.setText(menuCart.getMenu_qty() + "");
        }
        return convertView;
    }
}
