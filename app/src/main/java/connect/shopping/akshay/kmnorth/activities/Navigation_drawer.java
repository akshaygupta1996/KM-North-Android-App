package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teliver.sdk.core.Teliver;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.Fragments.FragmentA;
import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.SharedPrefManager;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.local.FirebaseOrder;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;
import connect.shopping.akshay.kmnorth.bean.other.MainCategory;
import connect.shopping.akshay.kmnorth.bean.response.Address;
import connect.shopping.akshay.kmnorth.bean.response.AddressResponse;
import connect.shopping.akshay.kmnorth.bean.response.ArrayMenuResponse;
import connect.shopping.akshay.kmnorth.bean.response.MainCategoryListResponse;
import connect.shopping.akshay.kmnorth.bean.response.MenuResponse;

import static connect.shopping.akshay.kmnorth.WebServiceType.GET_ADDRESS;

public class Navigation_drawer extends KMNorthActivity implements NavigationView.OnNavigationItemSelectedListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {

    private  NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<MainCategory> listMainCategory = new ArrayList<>();
    private List<ArrayMenuResponse> menuResponse;
    private int mainCategorySize;
    private List<MenuCart> menucartitems;
    private ProgressBar mainProgressBar;
    private ImageView imgLoading;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private OAuthDetailsRequestResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        user = LocalDataServiceImpl.getInstance(mApp).getUser();

//        mainProgressBar = (ProgressBar)findViewById(R.id.mainProgressbar);
//        mainProgressBar.setVisibility(View.VISIBLE);

        imgLoading = (ImageView)findViewById(R.id.imgLoading);
        imgLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.raw.loader).asGif().into(imgLoading);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ZopimChat.init("53hxxUSkMVpKndk9uKdDl0obvoT9SWVP");

        getMenu();




        final int order = SharedPrefManager.getInstance(getApplicationContext()).getOrder();

        if(order!=0) {

            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference();

//            if(mFirebaseDatabase.child("orders").child(String.valueOf(order))!=null) {

                mFirebaseDatabase.child("orders").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

//                Log.d(TAG, dataSnapshot.getValue(String.class));
//                    GenericTypeIndicator<Map<String, FirebaseOrder>> genericTypeIndicator = new GenericTypeIndicator<Map<String, FirebaseOrder>>() {};
//                    Map<String, FirebaseOrder> hashMap = dataSnapshot.getValue(genericTypeIndicator);
//
//                    for (Map.Entry<String,FirebaseOrder> entry : hashMap.entrySet()) {
//                        order = entry.getValue();

                        if (dataSnapshot.child(String.valueOf(order)).exists()) {


                            FirebaseOrder order_f = dataSnapshot.child(String.valueOf(order)).getValue(FirebaseOrder.class);
                            if (order_f.getStatus().equals("4")) {

                                Teliver.stopTracking(String.valueOf(order));
                                SharedPrefManager.getInstance(getApplicationContext()).clearOrder();
                                mFirebaseDatabase.child("orders").child(String.valueOf(order)).removeValue();
                                finish();


                            }


//                    ordersAdaptor.notifyDataSetChanged();
//                    Gson gsonObj = new Gson();
//                    Log.d("FIREBASE VALUS", gsonObj.toJson(order));

                        }else{
                            SharedPrefManager.getInstance(getApplicationContext()).clearOrder();
                        }
                    }
                    //   }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Fire", "Failed to read value.", error.toException());
                    }
                });
            }else{
                SharedPrefManager.getInstance(getApplicationContext()).clearOrder();


        }

//        Intent intent = this.getIntent();
//        Bundle bundle = intent.getExtras();
//
//        if(bundle!=null) {
//            menuResponse = (List<ArrayMenuResponse>) bundle.getSerializable("menu");
//        }
//        mainCategorySize = menuResponse.size();






    //    getMainCategory();
//        setupViewPager(viewPager);



        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getMainCategory() {


        showLoading(false);

        WebDataServiceImpl.getInstance(mApp).getMainMenuCategoryList(WebServiceType.GET_MAIN_CATEGORY_LIST,MainCategoryListResponse.class, null,this, this);




    }


    public void getMenu(){
        WebDataServiceImpl.getInstance(mApp).getMenuList(WebServiceType.GET_ALL_MENU_ITEM, MenuResponse.class, null,this, this);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        for(int i=1;i<= menuResponse.size();i++) {

            FragmentA fragment =  new FragmentA();
            Bundle bundle = new Bundle();
            bundle.putSerializable("submenu", (Serializable) menuResponse.get(i-1).getAllitems());
            fragment.setArguments(bundle);

            adaptor.addFragment(fragment, menuResponse.get(i-1).getMaincategory().getCat_name());
//            bundle.putInt("category_id", listMainCategory.get(i-1).getUniqueId());
//            fragment.setArguments(bundle);
//            adaptor.addFragment(fragment, listMainCategory.get(i-1).getCat_name());
        }
//        }
//        adaptor.addFragment(new FragmentA(), "North Indian Starter");
//        adaptor.addFragment(new FragmentB(), "Combos");
//        adaptor.addFragment(new FragmentC(),"Dinner & Rice");
//        adaptor.addFragment(new FragmentD(), "Breads");
        viewPager.setAdapter(adaptor);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu, menu);

        int order = SharedPrefManager.getInstance(getApplicationContext()).getOrder();
        MenuItem trackItem = (MenuItem) menu.findItem(R.id.menu_track);
        if(order!=0){
            trackItem.setVisible(true);
        }else{
            trackItem.setVisible(false);
        }

        menucartitems = MenuCart.listAll(MenuCart.class);

        int size = menucartitems.size();
        invalidateOptionsMenu();
        MenuItem menuItem = menu.findItem(R.id.menu_cart);
        menuItem.setIcon(buildCounterDrawable(size, R.drawable.menucart));

        return true;
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_cart) {

            menucartitems = MenuCart.listAll(MenuCart.class);
            if(menucartitems.size()==0){

                Toast.makeText(mApp, "Cart Is Empty", Toast.LENGTH_SHORT).show();

            }else {

                Intent intent = new Intent(getApplicationContext(), MenuCartActivity.class);
                startActivity(intent);
            }
        }else if(id == R.id.menu_track){


            int order = SharedPrefManager.getInstance(getApplicationContext()).getOrder();
            if(order == 0){
                Toast.makeText(mApp, "No Active Order", Toast.LENGTH_SHORT).show();
            }else {

                Intent intent = new Intent(this, TrackOrderFromActionActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }


        }
//        } else if (id == R.id.menuAction) {
//            Intent intent = new Intent(getApplicationContext(), MyBasketActivity.class);
//            intent.putExtra("user_id", user_id);
//            intent.putExtra("fname", fname);
//            intent.putExtra("lname", lname);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GET_ADDRESS:
                    if (response.getData() instanceof AddressResponse) {

                        AddressResponse addressResponse = (AddressResponse)response.getData();
                        ArrayList<Address> addresses = addressResponse.getAddress();
                        if(addresses.size()==0){
                            Intent i = new Intent(Navigation_drawer.this,AddressBook_add.class);
                            startActivity(i);
                        }else{

                            Intent i = new Intent(Navigation_drawer.this,SavedAddress_list.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("addessesList", addresses);
                            i.putExtras(bundle);
                            startActivity(i);
                        }


                    }
                    break;
                case GET_MAIN_CATEGORY_LIST:
                    if (response.getData() instanceof MainCategoryListResponse) {

                        MainCategoryListResponse response1 = (MainCategoryListResponse)response.getData();

                        boolean status = response1.isStatus();
                        if(status){

                         listMainCategory = response1.getCategory();
//                            setupViewPager(viewPager);
                        }

                        return;
                    }
                    break;
                case GET_ALL_MENU_ITEM:
                    if (response.getData() instanceof MenuResponse) {

                        MenuResponse response1 = (MenuResponse) response.getData();
                        if(response1.isStatus()){

                            imgLoading.setVisibility(View.GONE);
//                            mainProgressBar.setVisibility(View.GONE);
                             menuResponse = (List<ArrayMenuResponse>) response1.getMenu();
                            mainCategorySize = menuResponse.size();
                            setupViewPager(viewPager);

//                            Intent i = new Intent(getApplicationContext(),Navigation_drawer.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("menu", (Serializable) menuResponse);
//                            i.putExtras(bundle);
//                            startActivity(i);


                        }else{
                            Toast.makeText(mApp, "Error Loading Menu!! Try Again", Toast.LENGTH_SHORT).show();
                        }

                    }


                    break;
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyResponseBean volleyResponseBean, String errorMessage) {

        hideLoading();
    }

    @Override
    public void onNetworkUnavailable(WebServiceType webServiceType) {

        hideLoading();
        Toast.makeText(mApp, "No Network Connection", Toast.LENGTH_SHORT).show();

    }




    class ViewPagerAdaptor extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdaptor(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_address_book) {


            showLoading(false);
            String user_id = LocalDataServiceImpl.getInstance(mApp).getOAuthRequestResponse().getUser_id();
            WebDataServiceImpl.getInstance(mApp).getAddress(GET_ADDRESS, AddressResponse.class, user_id, this, this);
        }else if(id == R.id.nav_logout){

            LocalDataServiceImpl.getInstance(mApp).clearDatabase();
            SharedPrefManager.getInstance(mApp).clearToken();
            SharedPrefManager.getInstance(mApp).clearOrder();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
            finishAffinity();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }else if(id == R.id.nav_promo_code){

            Intent intent = new Intent(this, PromoCodeActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_order_history){
            Intent intent = new Intent(this, OrderHistoryActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_chat){

            OAuthDetailsRequestResponse user= LocalDataServiceImpl.getInstance(mApp).getUser();

            VisitorInfo visitorData = new VisitorInfo.Builder()
                    .name(user.getFname()+" "+user.getLname())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhone_number())
                    .build();

            ZopimChat.setVisitorInfo(visitorData);

            startActivity(new Intent(getApplicationContext(), ZopimChatActivity.class));

        }else if(id == R.id.nav_profile){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }else if(id  == R.id.nav_share){

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "KM North Application");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Download KM North App "+"https://play.google.com/store/apps/details?id=connect.shopping.akshay.kmnorth"+" and get discount using my Reference Code :" +user.getRefcode() );
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
