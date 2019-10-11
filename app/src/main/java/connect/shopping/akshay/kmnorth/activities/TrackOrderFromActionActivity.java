package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.local.FirebaseOrder;
import connect.shopping.akshay.kmnorth.bean.response.Address;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;

public class TrackOrderFromActionActivity extends KMNorthActivity implements View.OnClickListener {


    private TextView txtBookingId;
    private TextView txtAmoutnPayable;
    private TextView txtAddress;
    private TextView txtInstructions;
    private TextView txtDeliveryArea;
    private ProgressBar progressBar;
    private CardView cardView;

    private Button btnTrackOrder;
    private Button btnViewOrder;
    private MenuOrderResponse menuOrderResponse;
    private Address address;
    private int order_no;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseOrder order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order_from_action);



        setTitle("Order Details");


        txtBookingId = (TextView)findViewById(R.id.txtBookingid);
        txtAmoutnPayable = (TextView)findViewById(R.id.txtAmountPayable);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtDeliveryArea = (TextView)findViewById(R.id.txtDeliveryArea);
        txtInstructions = (TextView)findViewById(R.id.txtInstructions);

        btnTrackOrder = (Button)findViewById(R.id.btnTrackOrder);
        btnViewOrder = (Button)findViewById(R.id.btnViewOrder);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        cardView = (CardView)findViewById(R.id.cardview);

        cardView.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);

        order_no = getIntent().getIntExtra("order", 0);
        if(order_no == 0){

            Toast.makeText(mApp, "No Active Orders", Toast.LENGTH_SHORT).show();
        }else{


            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference();


            mFirebaseDatabase.child("orders").child(String.valueOf(order_no)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                Log.d(TAG, dataSnapshot.getValue(String.class));
//                    GenericTypeIndicator<Map<String, FirebaseOrder>> genericTypeIndicator = new GenericTypeIndicator<Map<String, FirebaseOrder>>() {};
//                    Map<String, FirebaseOrder> hashMap = dataSnapshot.getValue(genericTypeIndicator);
//
//                    for (Map.Entry<String,FirebaseOrder> entry : hashMap.entrySet()) {
//                        order = entry.getValue();

                    if(dataSnapshot.exists()) {

                        order = dataSnapshot.getValue(FirebaseOrder.class);
                        progressBar.setVisibility(View.GONE);
                        cardView.setVisibility(View.VISIBLE);

                        display();
                    }




//                    ordersAdaptor.notifyDataSetChanged();
//                    Gson gsonObj = new Gson();
//                    Log.d("FIREBASE VALUS", gsonObj.toJson(order));

                    }
             //   }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            btnTrackOrder.setOnClickListener(this);
            btnViewOrder.setOnClickListener(this);


        }
    }


    public void display(){

        txtBookingId.setText(order.getOrder().getOrder_id()+"");
        txtAmoutnPayable.setText("\u20B9 "+order.getPayment().getAmount_payable()+"");

        txtAddress.setText(order.getAddress().getAddress());
        txtDeliveryArea.setText(order.getAddress().getDelivery_area());
        txtInstructions.setText(order.getAddress().getInstructions());


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnViewOrder:

                menuOrderResponse = new MenuOrderResponse();
                menuOrderResponse.setOrder(order.getOrder());
                menuOrderResponse.setMenu(order.getMenu());
                menuOrderResponse.setPayment(order.getPayment());
                menuOrderResponse.setStatus(true);

                Intent intent = new Intent(this, ViewOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("menuorder", menuOrderResponse);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.btnTrackOrder:

                Intent intent1 = new Intent(this, OrderTrackingActivity.class);
                intent1.putExtra("order_no", order_no+"");
                startActivity(intent1);
                break;

        }

    }
}
