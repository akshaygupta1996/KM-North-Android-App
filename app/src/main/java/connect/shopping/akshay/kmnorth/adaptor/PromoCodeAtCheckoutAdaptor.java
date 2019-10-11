package connect.shopping.akshay.kmnorth.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.other.UserPromo;

/**
 * Created by Akshay on 24-07-2017.
 */

public class PromoCodeAtCheckoutAdaptor extends BaseAdapter {


    private List<UserPromo> userPromoList;
    private Activity activity;
    private LayoutInflater inflater;


    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public PromoCodeAtCheckoutAdaptor(Activity activity, List<UserPromo> userPromoList) {
        this.activity = activity;
        this.userPromoList = userPromoList;
    }

    @Override
    public int getCount() {
        return userPromoList.size();
    }

    @Override
    public Object getItem(int i) {
        return userPromoList.get(i);
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
            convertView = inflater.inflate(R.layout.item_checkout_promo_code, viewGroup,false);


        Button txtPromoCode;

        txtPromoCode = (Button)convertView.findViewById(R.id.txtPromCodeAtCheckout);



        final UserPromo userPromo = userPromoList.get(i);

        txtPromoCode.setText(userPromo.getPromo_code());


        txtPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mOnDataChangeListener != null){
                    mOnDataChangeListener.onDataChanged(userPromo);
                }

            }
        });


//        cvMenuItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(activity, MenuPopUpActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("menuitem",menuItem);
//
//                intent.putExtras(bundle);
//
//                activity.startActivity(intent);
//            }
//        });

        return convertView;
    }

    public interface OnDataChangeListener{
        public void onDataChanged(UserPromo promo);
    }
}

