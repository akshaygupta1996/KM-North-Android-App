package connect.shopping.akshay.kmnorth.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.GPSTracker;
import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.response.Address;
import connect.shopping.akshay.kmnorth.bean.response.DeleteAddressResponse;

import static connect.shopping.akshay.kmnorth.WebServiceType.DELETE_ADDRESS;


public class SavedAddress_list extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener  {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private Button btn_new_addr;
    private SwipeMenuListView listView;
//    private AddressListData addressListData;
    private ArrayList<Address> addressList;
    GPSTracker gps;
    double latitude;
    double longitude;
    private static final String SUBLOCALITY = "SUBLOCALITY";
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String LOCALITY = "LOCALITY";
    // flag for GPS status
    boolean isGPSEnabled = false;
    private CustomAdapter customAdapter;

    // flag for network status
    boolean isNetworkEnabled = false;
    protected LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_address_list);
        btn_new_addr = (Button)findViewById(R.id.btn_new_add);
        listView = (SwipeMenuListView)findViewById(R.id.addresslistView);

        btn_new_addr.setOnClickListener(this);

        checkLocationPermission();

        final Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();

        addressList = (ArrayList<Address>) bundle.getSerializable("addessesList");



//         addressListData = new AddressListData();
//        Demolist demolist = new Demolist();
//        addressListData.setArea(demolist.getArea());
//        addressListData.setAddressid(demolist.getAddressid());
//        addressListData.setComplete_area(demolist.getComplete_area());
//        addressListData.setInstruction(demolist.getInstruction());


        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x37, 0x97,
                        0x28)));
                // set item width
                openItem.setWidth(200);

                // set item title
                openItem.setTitle("EDIT");
                // set item title fontsize379728
                openItem.setTitleSize(14);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item backgroundD32F2F
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xD3,
                        0x2F, 0x2F)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setTitle("DELETE");
                deleteItem.setTitleSize(14);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);


        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        Intent intent1 = new Intent(getApplicationContext(), AddCompleteAddress.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("address", addressList.get(position));
                        bundle1 .putBoolean("editaddress", true);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
//                        Toast.makeText(getApplicationContext(),"hello "+index,Toast.LENGTH_SHORT).show();
                        // open
                        break;
                    case 1:
//                        Toast.makeText(getApplicationContext(),"hello 1"+index,Toast.LENGTH_SHORT).show();


                        deleteAddress(addressList.get(position).getUniqueId());
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


      /*  mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                Toast.makeText(getApplicationContext(),"hello "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeEnd(int position) {
                Toast.makeText(getApplicationContext(),"bye "+position,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void deleteAddress(int id) {


        showLoading(false);
        WebDataServiceImpl.getInstance(mApp).deleteAddress(DELETE_ADDRESS, DeleteAddressResponse.class, id, this, this);

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

            btn_new_addr.setClickable(true);

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
                                ActivityCompat.requestPermissions(SavedAddress_list.this,
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

                        btn_new_addr.setClickable(false);
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
        if (resultCode == 0) {
            switch (requestCode) {
                case 1:
                    locationManager = (LocationManager)
                            getSystemService(LOCATION_SERVICE);
                    isGPSEnabled = locationManager
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(isGPSEnabled){
                        btn_new_addr.setClickable(true);
                    }
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_new_add:

                final Geocoder gc = new Geocoder(this);

                // create class object
                gps = new GPSTracker(SavedAddress_list.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    List<android.location.Address> list = null;
                    try {
                        list = gc.getFromLocation(latitude,longitude,1);

                        android.location.Address address = list.get(0);
                        String locality = address.getLocality();
                        String sublocality = address.getSubLocality();
                        Intent i = new Intent(SavedAddress_list.this,AddCompleteAddress.class);
                        Bundle extras = new Bundle();
                        extras.putBoolean("editaddress", false);
                        extras.putString(LOCALITY,locality);
                        extras.putString(SUBLOCALITY,sublocality);
                        i.putExtras(extras);

                        startActivity(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    // \n is for new line
                    // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }





                break;
        }

    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case DELETE_ADDRESS:

                    if (response.getData() instanceof DeleteAddressResponse) {

                        DeleteAddressResponse deleteAddressResponse = (DeleteAddressResponse)response.getData();
                        if(deleteAddressResponse.isStatus()){

                            addressList = (ArrayList<Address>) deleteAddressResponse.getAddress();
                            customAdapter.notifyDataSetChanged();

                        }


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


    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return addressList.size();
        }

        @Override
        public Object getItem(int i) {
            return addressList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return addressList.get(i).getUniqueId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listview_item,null);

            TextView area1 = (TextView)view.findViewById(R.id.list_area);
            TextView comp_area1 = (TextView)view.findViewById(R.id.list_comp_addr);
            TextView inst1 = (TextView)view.findViewById(R.id.list_instruction);

            Address address = addressList.get(i);

            area1.setText(address.getDelivery_area());
            comp_area1.setText(address.getAddress());
            inst1.setText(address.getInstructions());

            return view;
        }
    }

}
