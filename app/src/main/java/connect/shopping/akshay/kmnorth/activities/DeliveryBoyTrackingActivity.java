package connect.shopping.akshay.kmnorth.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.core.TrackingListener;
import com.teliver.sdk.models.MarkerOption;
import com.teliver.sdk.models.TLocation;
import com.teliver.sdk.models.TrackingBuilder;

import java.text.DecimalFormat;

import connect.shopping.akshay.kmnorth.GPSTracker;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.SharedPrefManager;
import connect.shopping.akshay.kmnorth.bean.local.FirebaseOrder;

public class DeliveryBoyTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String order_no;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseOrder order;
    private SupportMapFragment supportMapFragment;
    private TextView txtDistance;
    GoogleMap map;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    GPSTracker gps;
    double latitude;
    double longitude;
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_tracking);

        txtDistance = (TextView)findViewById(R.id.txtDistance);


        order_no = getIntent().getStringExtra("order_no");


//        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map2);
//        mapFrag.getMapAsync(this);
        System.gc();
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                R.id.map)).getMapAsync(this);
//        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);


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

                order = dataSnapshot.getValue(FirebaseOrder.class);
                if (order.getStatus().equals("4")) {

                    Teliver.stopTracking(String.valueOf(order_no));
                    SharedPrefManager.getInstance(getApplicationContext()).clearOrder();
                    mFirebaseDatabase.child("orders").child(String.valueOf(order_no)).removeValue();
                    Intent intent = new Intent(getApplicationContext(), Navigation_drawer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }


//                    ordersAdaptor.notifyDataSetChanged();
//                    Gson gsonObj = new Gson();
//                    Log.d("FIREBASE VALUS", gsonObj.toJson(order));
            }
            //   }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Fire", "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        checkLocationPermission();

        gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            LatLng myLocation = null;

                myLocation = new LatLng(latitude,
                        longitude);

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    (float) 1.6));

            if(order_no.equals("")){

                Toast.makeText(this, "Somthing Went Wrong...!!!", Toast.LENGTH_SHORT).show();
            }else{


                MarkerOption option = new MarkerOption(order_no);
                option.setIconMarker(R.drawable.scooter);

                Teliver.startTracking(new TrackingBuilder(option).withYourMap(map).withListener(new TrackingListener() {
                    @Override
                    public void onTrackingStarted(String trackingId) {

                    }

                    @Override
                    public void onLocationUpdate(String trackingId, TLocation location) {


                        Location startLocation = new Location("START");
                        startLocation.setLatitude(latitude);
                        startLocation.setLongitude(longitude);

                        Location endLocation = new Location("END");
                        endLocation.setLatitude(location.getLatitude());
                        endLocation.setLongitude(location.getLongitude());


                        double distance = startLocation.distanceTo(endLocation)/1000;

                        DecimalFormat dtime = new DecimalFormat("#.##");
                        double i = Double.valueOf(dtime.format(distance));
                        txtDistance.setText(String.valueOf(i)+" KM Left");





//                    map.clear();

                    }

                    @Override
                    public void onTrackingEnded(String trackingId) {

                    }

                    @Override
                    public void onTrackingError(String reason) {

                    }
                }).build());
            }


            // \n is for new line
            // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }











    }


    public void checkGPS(){

        locationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled) {
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(myIntent,1);
        }else {


        }

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(DeliveryBoyTrackingActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            checkGPS();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


//                        btnShowLocation.setClickable(true);
                        checkGPS();
                        //Request location updates:
//                        locationManager.requestLocationUpdates(provider, 400, 1, this);
//                    }

                    } else {

//                        btnShowLocation.setClickable(false);
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                    }
                    return;
                }

            }
        }
    }
}
