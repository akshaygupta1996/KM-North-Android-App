package connect.shopping.akshay.kmnorth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.activities.MenuPopUpActivity;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;
import connect.shopping.akshay.kmnorth.bean.other.MenuItem;
import connect.shopping.akshay.kmnorth.bean.response.ArrayInsideMainResponse;

/**
 * Created by Akshay on 16-07-2017.
 */

public class MenuAdaptor extends StickyHeaderGridAdapter {

    private List<List<MenuItem>> labels;
    private List<ArrayInsideMainResponse> menu;
    private Context mContext;

    public MenuAdaptor(Context context,int sections, ArrayList<Integer> count, List<ArrayInsideMainResponse> submenu) {

        mContext = context;

        menu = submenu;

        labels = new ArrayList<>(sections);
        for (int s = 0; s < sections; ++s) {
            List<MenuItem> labels = submenu.get(s).getItems();
//            for (int i = 0; i < count.get(s); ++i) {
//                String label = "Item " + String.valueOf(i);
//            /*for (int p = 0; p < s - i; ++p) {
//               label += "*\n";
//            }*/
//                labels.add(label);
//            }
            this.labels.add(labels);
        }
    }

    @Override
    public int getSectionCount() {
        return labels.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return labels.get(section).size();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_view, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder)viewHolder;
        final String label = "Header " + section;
        holder.labelView.setText(menu.get(section).getSubcategory().getCat_name());
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder)viewHolder;
        final MenuItem label = labels.get(section).get(position);
        holder.txtMenuName.setText(label.getName());
        holder.txtDescription.setText(label.getDescription());
        holder.txtHalfPrice.setVisibility(View.INVISIBLE);
        if(label.getHalf_price() == 0){

            holder.txtFullPrice.setText(" \u20B9 "+label.getFull_price()+"");
//            holder.txtHalfPrice.setVisibility(View.INVISIBLE);
        }else{

            holder.txtFullPrice.setText(" \u20B9 "+label.getFull_price()+" / "+label.getHalf_price());
//            holder.txtHalfPrice.setVisibility(View.VISIBLE);
//            holder.txtHalfPrice.setText("/ "+label.getHalf_price()+"");
        }
        holder.imgMenuAddedToCart.setVisibility(View.INVISIBLE);
        Glide.with(mContext).load("http://res.cloudinary.com/kmnorth/image/upload/v1503858950/"+label.getUniqueId()+".jpg").into(holder.imgFood);

        MenuCart menuCart = MenuCart.first(MenuCart.class);
        if(menuCart!=null){

            ArrayList<MenuCart> menu = (ArrayList<MenuCart>) MenuCart.findWithQuery(MenuCart.class, "Select * from menu_cart where menuid = ?", label.getUniqueId()+"");

            for(int z=0;z<menu.size();z++) {
                Log.d("MENU", menu.get(z).getMenuitem().getName() + "   ");
            }
            if(menu.size()!=0){
                holder.imgMenuAddedToCart.setVisibility(View.VISIBLE);
            }else{
                holder.imgMenuAddedToCart.setVisibility(View.INVISIBLE);}
        }





        holder.cvMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MenuPopUpActivity.class);
                Bundle bundle = new Bundle();



                bundle.putSerializable("menuitem",label);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });
//        holder.labelView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int section = getAdapterPositionSection(holder.getAdapterPosition());
//                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
//
//                labels.get(section).remove(offset);
//                notifySectionItemRemoved(section, offset);
//                Toast.makeText(holder.labelView.getContext(), label, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public static class MyHeaderViewHolder extends HeaderViewHolder {
        TextView labelView;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);
        }
    }

    public static class MyItemViewHolder extends ItemViewHolder {
        TextView txtMenuName;
        TextView txtDescription;
        TextView txtFullPrice;
        TextView txtHalfPrice;
        CardView cvMenuItem;
        ImageView imgMenuAddedToCart;
        ImageView imgFood;


        MyItemViewHolder(View itemView) {
            super(itemView);

            txtMenuName = (TextView)itemView.findViewById(R.id.txtMenuName);
            txtDescription = (TextView)itemView.findViewById(R.id.txtMenuDescription);
            txtFullPrice = (TextView)itemView.findViewById(R.id.txtMenuFullPrice);
            txtHalfPrice = (TextView)itemView.findViewById(R.id.txtMenuHalfPrice);
            cvMenuItem = (CardView)itemView.findViewById(R.id.cvMenuItem);
            imgMenuAddedToCart = (ImageView)itemView.findViewById(R.id.imgMenuAddedToCart);
            imgFood = (ImageView)itemView.findViewById(R.id.imageMenu);

        }
    }
}
