package connect.shopping.akshay.kmnorth.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.activities.ViewOrderActivity;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;

/**
 * Created by Akshay on 23-07-2017.
 */

public class OrderHistoryAdaptor extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<MenuOrderResponse> listMenuOrder;

    public OrderHistoryAdaptor(Activity activity, List<MenuOrderResponse> listMenuOrder) {
        this.activity = activity;
        this.listMenuOrder= listMenuOrder;


    }
    @Override
    public int getCount() {
        return listMenuOrder.size();
    }

    @Override
    public Object getItem(int i) {

        return listMenuOrder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        convertView = activity.getLayoutInflater().inflate(R.layout.item_order_history,viewGroup,false);


        TextView txtOrderNumber = (TextView)convertView.findViewById(R.id.txtOrderNumber);
        TextView txtOrderDate = (TextView)convertView.findViewById(R.id.txtOrderDate);
        TextView txtOrderAmount = (TextView)convertView.findViewById(R.id.txtOrdAmount);
        Button btnViewOrder = (Button)convertView.findViewById(R.id.btnViewDetails);


        final MenuOrderResponse menuOrderResponse =  listMenuOrder.get(i);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date testDate = null;
        try {
            testDate = sdf.parse(menuOrderResponse.getPayment().getDate_time_of_payment()+"");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String newFormat = formatter.format(testDate);


        txtOrderNumber.setText(menuOrderResponse.getOrder().getOrder_id()+"");
        txtOrderAmount.setText("\u20B9 "+menuOrderResponse.getPayment().getAmount_payable()+"");
        txtOrderDate.setText(newFormat);

        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("menuorder", menuOrderResponse);
                Intent intent = new Intent(activity, ViewOrderActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }


}
