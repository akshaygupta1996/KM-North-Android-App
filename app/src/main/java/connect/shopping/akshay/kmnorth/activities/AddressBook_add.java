package connect.shopping.akshay.kmnorth.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import connect.shopping.akshay.kmnorth.GPSTracker;
import connect.shopping.akshay.kmnorth.R;

public class AddressBook_add extends KMNorthActivity {


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    Button btnShowLocation;
    GPSTracker gps;
    double latitude;
    double longitude;
    private static final String SUBLOCALITY = "SUBLOCALITY";
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String LOCALITY = "LOCALITY";
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    protected LocationManager locationManager;
    public AddressBook_add() throws IOException {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_add);
        btnShowLocation = (Button) findViewById(R.id.addAdrr);

        btnShowLocation.setClickable(false);
        final Geocoder gc = new Geocoder(this);



        checkLocationPermission();

        // getting GPS status


        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                //show dialog

                // create class object
                gps = new GPSTracker(AddressBook_add.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    // \n is for new line
                    // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                
                
                


                List <Address> list = null;
                try {
                    list = gc.getFromLocation(latitude,longitude,1);
                    if(list!=null) {

                        Address address = list.get(0);
                        String locality = address.getLocality();
                        String sublocality = address.getSubLocality();
                        Log.d("locality", locality+"  "+sublocality);
                        Intent i = new Intent(AddressBook_add.this, AddCompleteAddress.class);
                        Bundle extras = new Bundle();
                        extras.putString(LOCALITY, locality);
                        extras.putString(SUBLOCALITY, sublocality);
                        i.putExtras(extras);

                        startActivity(i);
                    }else{

                        Toast.makeText(gps, "Try Again.!!!", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                


                //Toast.makeText(getApplicationContext(),"loca " + locality + "sublocal " + sublocality , Toast.LENGTH_LONG).show();
            }




        });



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

            btnShowLocation.setClickable(true);

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
                                ActivityCompat.requestPermissions(AddressBook_add.this,
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

                        btnShowLocation.setClickable(false);
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                    }
                    return;
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            switch (requestCode) {
                case 1:
                    if(isGPSEnabled){
                        btnShowLocation.setClickable(true);
                    }
            }
        }
    }




}