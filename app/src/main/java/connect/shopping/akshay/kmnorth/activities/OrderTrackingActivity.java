package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.local.FirebaseOrder;

public class OrderTrackingActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView imgTrack;
    private Button btnTrack;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String order_no;
    private FirebaseOrder order;
    private String TAG = "TRACK";
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);


        setTitle("Track Order");


        imgTrack = (ImageView)findViewById(R.id.imgTracking);

        btnTrack = (Button)findViewById(R.id.btnTrackDeliveryBoy);
        btnTrack.setOnClickListener(this);
        order_no = getIntent().getStringExtra("order_no");

        Log.d("order_no", order_no+"");


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference();

        if(mFirebaseDatabase.child("orders").child(order_no)!=null) {
            mFirebaseDatabase.child("orders").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                Log.d(TAG, dataSnapshot.getValue(String.class));


                    if(dataSnapshot.child(order_no).exists()) {


                        FirebaseOrder order = dataSnapshot.child(order_no).getValue(FirebaseOrder.class);
//
//
//                GenericTypeIndicator<Map<String, FirebaseOrder>> genericTypeIndicator = new GenericTypeIndicator<Map<String, FirebaseOrder>>() {};
//                Map<String, FirebaseOrder> hashMap = dataSnapshot.getValue(genericTypeIndicator);
//
//                for (Map.Entry<String,FirebaseOrder> entry : hashMap.entrySet()) {
//                    order = entry.getValue();

                        status = Integer.parseInt(order.getStatus());
                        switch (status) {

                            case 0:
                                imgTrack.setImageDrawable(getResources().getDrawable(R.drawable.orderconfirmednew));
                                break;
                            case 1:
                                imgTrack.setImageDrawable(getResources().getDrawable(R.drawable.orderdeliverynew));
                                break;
                            case 2:
                                imgTrack.setImageDrawable(getResources().getDrawable(R.drawable.orderkitchennew));
                                break;
                            case 3:
                                imgTrack.setImageDrawable(getResources().getDrawable(R.drawable.orderplacednew));
                                break;


//                    }

//                    ordersAdaptor.notifyDataSetChanged();
//                    Gson gsonObj = new Gson();
//                    Log.d("FIREBASE VALUS", gsonObj.toJson(order));

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }else{
            Intent intent = new Intent(this, Navigation_drawer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnTrackDeliveryBoy:

                if(status==3){
                 //   SharedPrefManager.getInstance(getApplicationContext()).clearOrder();

                    Intent intent = new Intent(getApplicationContext(), DeliveryBoyTrackingActivity.class);
                    intent.putExtra("order_no", order_no);
                    startActivity(intent);


                }else{
                    Toast.makeText(this, "Order Yet to be Out for Delivery..", Toast.LENGTH_SHORT).show();
                }
        }

    }
}
