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
import connect.shopping.akshay.kmnorth.bean.other.UserPromo;

/**
 * Created by Akshay on 23-07-2017.
 */

public class PromoCodeAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<UserPromo> listPromoCode;

    public PromoCodeAdaptor(Activity activity, List<UserPromo> listPromoCode) {
        this.activity = activity;
        this.listPromoCode = listPromoCode;

        Log.d("ADAPTOR", listPromoCode.size()+"");

    }
    @Override
    public int getCount() {
        return listPromoCode.size();
    }

    @Override
    public Object getItem(int i) {

        return listPromoCode.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        convertView = activity.getLayoutInflater().inflate(R.layout.item_promo_codes,viewGroup,false);

        TextView txtPromoCode = (TextView)convertView.findViewById(R.id.txtpromocode1);
        TextView txtPromoDiscount = (TextView)convertView.findViewById(R.id.txtpromodiscount);
        TextView txtPromoDesription = (TextView)convertView.findViewById(R.id.txtPromoDescription);
        TextView txtPromoValidity = (TextView)convertView.findViewById(R.id.txtPromoValidity);

        UserPromo promoCode = listPromoCode.get(i);


        txtPromoCode.setText(promoCode.getPromo_code());
        if(promoCode.isPromo_wallet()){
            txtPromoDiscount.setText(promoCode.getPromo_discount_per()+"%" + "  Cashback");
        }else{

            txtPromoDiscount.setText(promoCode.getPromo_discount_per()+"%"+  "  Discount");
        }

        txtPromoDesription.setText(promoCode.getPromo_description());

        txtPromoValidity.setText("Validity : " +promoCode.getUserpromo_validity());

        return convertView;
    }
}
