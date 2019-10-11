package connect.shopping.akshay.kmnorth.adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.response.Address;

/**
 * Created by Akshay on 26-07-2017.
 */

public class SavedAddressAdaptor  extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Address> listAddress;

    SavedAddressAdaptor.OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(SavedAddressAdaptor.OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }



    public SavedAddressAdaptor(Activity activity, List<Address> listAddress) {
        this.activity = activity;
        this.listAddress = listAddress;


    }
    @Override
    public int getCount() {
        return listAddress.size();
    }

    @Override
    public Object getItem(int i) {

        return listAddress.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        convertView = activity.getLayoutInflater().inflate(R.layout.listview_item,viewGroup,false);



        final LinearLayout llAddress = (LinearLayout)convertView.findViewById(R.id.llAddress);


        TextView area1 = (TextView)convertView.findViewById(R.id.list_area);
        TextView comp_area1 = (TextView)convertView.findViewById(R.id.list_comp_addr);
        TextView inst1 = (TextView)convertView.findViewById(R.id.list_instruction);

        final Address address = listAddress.get(i);

        area1.setText(address.getDelivery_area());
        comp_area1.setText(address.getAddress());
        inst1.setText(address.getInstructions());

        llAddress.setBackgroundColor(activity.getResources().getColor(R.color.white));

        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                llAddress.setBackgroundColor(activity.getResources().getColor(R.color.green));
                if(mOnDataChangeListener != null){
                    mOnDataChangeListener.onDataChanged(address);
                }

            }
        });

        return convertView;
    }

    public interface OnDataChangeListener{
        public void onDataChanged(Address address);
    }
}

