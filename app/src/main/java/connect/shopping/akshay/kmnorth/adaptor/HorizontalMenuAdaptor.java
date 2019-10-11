package connect.shopping.akshay.kmnorth.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.activities.MenuPopUpActivity;
import connect.shopping.akshay.kmnorth.bean.other.MenuItem;

/**
 * Created by Akshay on 18-07-2017.
 */

public class HorizontalMenuAdaptor extends BaseAdapter {


    private List<MenuItem> menuItems;
    private Activity activity;
    private LayoutInflater inflater;

    public HorizontalMenuAdaptor(Activity activity, List<MenuItem> menuItems) {
        this.activity = activity;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int i) {
        return menuItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.horizontal_menu_layout, viewGroup,false);


        TextView txtMenuName;
        TextView txtDescription;
        TextView txtFullPrice;
        TextView txtHalfPrice;
        CardView cvMenuItem;
        ImageView imgMenuAddedToCart;
        ImageView imgMenu;

        imgMenu = (ImageView)convertView.findViewById(R.id.imageMenuHorizontal);

        txtMenuName = (TextView)convertView.findViewById(R.id.txtMenuName);
        txtDescription = (TextView)convertView.findViewById(R.id.txtMenuDescription);
        txtFullPrice = (TextView)convertView.findViewById(R.id.txtMenuFullPrice);
        txtHalfPrice = (TextView)convertView.findViewById(R.id.txtMenuHalfPrice);
        cvMenuItem = (CardView)convertView.findViewById(R.id.cvMenuItem);
        imgMenuAddedToCart = (ImageView)convertView.findViewById(R.id.imgMenuAddedToCart);


        final MenuItem menuItem = menuItems.get(i);

        txtMenuName.setText(menuItem.getName());
        txtDescription.setText(menuItem.getDescription());
        txtHalfPrice.setVisibility(View.INVISIBLE);

       Glide.with(activity).load("http://res.cloudinary.com/kmnorth/image/upload/v1503858950/"+menuItem.getUniqueId()+".jpg")
               .into(imgMenu);

        if(menuItem.getHalf_price() == 0){

            txtFullPrice.setText(" \u20B9 "+menuItem.getFull_price()+"");

        }else{

            txtFullPrice.setText(" \u20B9 "+menuItem.getFull_price()+" / "+menuItem.getHalf_price());

        }

        cvMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MenuPopUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("menuitem",menuItem);

                intent.putExtras(bundle);

                activity.startActivity(intent);
            }
        });
        imgMenuAddedToCart.setVisibility(View.INVISIBLE);

        return convertView;
    }
}
