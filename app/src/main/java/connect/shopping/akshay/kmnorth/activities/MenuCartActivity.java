package connect.shopping.akshay.kmnorth.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.List;

import connect.shopping.akshay.kmnorth.CenterLockHorizontalScrollview;
import connect.shopping.akshay.kmnorth.GPSTracker;
import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.adaptor.HorizontalMenuAdaptor;
import connect.shopping.akshay.kmnorth.adaptor.MenuCartAdaptor;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;
import connect.shopping.akshay.kmnorth.bean.other.MenuItem;
import connect.shopping.akshay.kmnorth.bean.response.MenuItemListResponse;

public class MenuCartActivity extends KMNorthActivity implements View.OnClickListener,  Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {

    private List<MenuCart> menucartitems;
    private TextView txtNumMenuCart;
    private TextView txtMenuCartPrice;
    private ListView lstMenuCart;
    private Button btnProceed;
    private MenuCartAdaptor menuCartAdaptor;
    private int subTotal = 0;
    private CenterLockHorizontalScrollview horizontalScrollView;
    private ProgressBar progressBar;
    private List<MenuItem> menuItemsH;
    private HorizontalMenuAdaptor horizontalMenuAdaptor;
    private LinearLayout llActivity;

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
        setContentView(R.layout.activity_menu_cart);


        txtNumMenuCart = (TextView)findViewById(R.id.txtNumMenuItems);
        txtMenuCartPrice = (TextView)findViewById(R.id.txtMenuCartTotal);
        progressBar = (ProgressBar)findViewById(R.id.pbHeaderProgress);

        lstMenuCart = (ListView)findViewById(R.id.lstMenuItems);
        llActivity = (LinearLayout)findViewById(R.id.activity_menu_cart);

        horizontalScrollView = (CenterLockHorizontalScrollview) findViewById(R.id.horizontalScrollView);




        btnProceed = (Button)findViewById(R.id.btnProceedToCheckOut);
        btnProceed.setOnClickListener(this);

        showData();

        showMenuToAddData();

    }

    private void showData() {

        subTotal = 0;
        menucartitems = MenuCart.listAll(MenuCart.class);

        for(int i =0 ;i<menucartitems.size();i++){
            subTotal = subTotal + menucartitems.get(i).getMenu_price();
        }


        menuCartAdaptor=new MenuCartAdaptor(this,menucartitems);
        lstMenuCart.setAdapter(menuCartAdaptor);

        lstMenuCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuItem menuItem= menucartitems.get(0).getMenuitem();
                Intent intent = new Intent(getApplicationContext(), MenuPopUpActivity.class);
                Bundle bundle = new Bundle();



                bundle.putSerializable("menuitem",menuItem);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


        txtNumMenuCart.setText(menucartitems.size()+"");
        txtMenuCartPrice.setText("\u20B9"+subTotal);

        Log.d("MENUCART", menucartitems.size()+"");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnProceedToCheckOut:

                checkLocationPermission();

                gps = new GPSTracker(this);

                // check if GPS enabled
                if(gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Location startLocation = new Location("START");
                    startLocation.setLatitude(latitude);
                    startLocation.setLongitude(longitude);

                    Location endLocation = new Location("END");
                    endLocation.setLatitude(20.354584);
                    endLocation.setLongitude(85.827318);


                    double distance = startLocation.distanceTo(endLocation) / 1000;

                    if(distance<=16) {

                        Intent intent = new Intent(this, OrderCheckOutActivity.class);
                        startActivity(intent);
                    }else{
                        Snackbar.make(llActivity,"We do not deliver at your area", Snackbar.LENGTH_LONG).show();
                    }

                }

                break;
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
                                ActivityCompat.requestPermissions(MenuCartActivity.this,
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
    protected void onResume() {
        super.onResume();
        Log.d("Menu Cart", "On Resume Called");
        showData();

    }


    public void showMenuToAddData(){

        progressBar.setVisibility(View.VISIBLE);

        WebDataServiceImpl.getInstance(mApp).getMenuItemList(WebServiceType.GET_MENU_LIST,MenuItemListResponse.class,5, null,this, this);



    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        progressBar.setVisibility(View.INVISIBLE);
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GET_MENU_LIST:
                    if (response.getData() instanceof MenuItemListResponse) {

                        MenuItemListResponse response1 = (MenuItemListResponse) response.getData();

                        boolean status = response1.isStatus();
                        if(status){

                            menuItemsH = response1.getItems();
                            if(menuItemsH.size()==0){



                            }else {

                                horizontalMenuAdaptor = new HorizontalMenuAdaptor(this,menuItemsH);
                                horizontalScrollView.setAdapter(this, horizontalMenuAdaptor);


                            }

                        }

                        return;
                    }
                    break;

            }
        }

    }

    @Override
    public void onErrorResponse(VolleyResponseBean volleyResponseBean, String errorMessage) {

    }

    @Override
    public void onNetworkUnavailable(WebServiceType webServiceType) {

    }
}
