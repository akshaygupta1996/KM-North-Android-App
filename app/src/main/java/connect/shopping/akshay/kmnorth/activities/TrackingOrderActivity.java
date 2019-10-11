package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.response.Address;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;

public class TrackingOrderActivity extends KMNorthActivity implements View.OnClickListener {

    private TextView txtBookingId;
    private TextView txtAmoutnPayable;
    private TextView txtAddress;
    private TextView txtInstructions;
    private TextView txtDeliveryArea;

    private Button btnTrackOrder;
    private Button btnViewOrder;
    private MenuOrderResponse menuOrderResponse;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_order);


        txtBookingId = (TextView)findViewById(R.id.txtBookingid);
        txtAmoutnPayable = (TextView)findViewById(R.id.txtAmountPayable);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtDeliveryArea = (TextView)findViewById(R.id.txtDeliveryArea);
        txtInstructions = (TextView)findViewById(R.id.txtInstructions);

        btnTrackOrder = (Button)findViewById(R.id.btnTrackOrder);
        btnViewOrder = (Button)findViewById(R.id.btnViewOrder);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        menuOrderResponse = (MenuOrderResponse) bundle.getSerializable("order");
        address = (Address)bundle.getSerializable("address");


        txtBookingId.setText(menuOrderResponse.getOrder().getOrder_id()+" ");
        txtAmoutnPayable.setText("\u20B9 "+menuOrderResponse.getPayment().getAmount_payable());

        txtAddress.setText(address.getAddress());
        txtDeliveryArea.setText(address.getDelivery_area());
        txtInstructions.setText(address.getInstructions());


        btnTrackOrder.setOnClickListener(this);
        btnViewOrder.setOnClickListener(this);



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, Navigation_drawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnViewOrder:

                Intent intent = new Intent(this, ViewOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("menuorder", menuOrderResponse);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.btnTrackOrder:


                Log.d("Order Id", menuOrderResponse.getOrder().getOrder_id()+"");
                Intent intent1 = new Intent(this, OrderTrackingActivity.class);
                Log.d("ORder No", menuOrderResponse.getOrder().getOrder_id()+"");
                intent1.putExtra("order_no", menuOrderResponse.getOrder().getOrder_id()+"");
                startActivity(intent1);
                break;

        }

    }
}
