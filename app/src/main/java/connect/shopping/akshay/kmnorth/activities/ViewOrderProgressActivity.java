package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import connect.shopping.akshay.kmnorth.R;

public class ViewOrderProgressActivity extends AppCompatActivity {

    private ImageView imgTrack;
    private Button btnStartTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_progress);


        imgTrack = (ImageView)findViewById(R.id.imgTrack);
        btnStartTracking = (Button)findViewById(R.id.btnTrack);
        btnStartTracking.setClickable(false);
    }
}
