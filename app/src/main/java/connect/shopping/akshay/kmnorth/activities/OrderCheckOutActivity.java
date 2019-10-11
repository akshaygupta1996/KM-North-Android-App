package connect.shopping.akshay.kmnorth.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.KMNorthConstants;
import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.PromoCodeHorizontalPromoScrollView;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.SharedPrefManager;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.adaptor.PromoCodeAtCheckoutAdaptor;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;
import connect.shopping.akshay.kmnorth.bean.local.Order;
import connect.shopping.akshay.kmnorth.bean.local.Payment;
import connect.shopping.akshay.kmnorth.bean.other.Tax;
import connect.shopping.akshay.kmnorth.bean.other.UserPromo;
import connect.shopping.akshay.kmnorth.bean.request.MenuOrderRequest;
import connect.shopping.akshay.kmnorth.bean.response.Address;
import connect.shopping.akshay.kmnorth.bean.response.AddressResponse;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;
import connect.shopping.akshay.kmnorth.bean.response.TaxResponse;
import connect.shopping.akshay.kmnorth.bean.response.UsersPromoCodeResponse;

import static connect.shopping.akshay.kmnorth.WebServiceType.FOOD_ORDER;
import static connect.shopping.akshay.kmnorth.WebServiceType.GET_ADDRESS;
import static connect.shopping.akshay.kmnorth.WebServiceType.GET_PROMO_CHECK_OUT;
import static connect.shopping.akshay.kmnorth.WebServiceType.GET_TAXES;

public class OrderCheckOutActivity extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {


    private static final int REQUEST_CODE = 201;
    private TextView txtName;
    private TextView txtPhoneNumber;
    private TextView txtbtnChangePhone;
    private TextView txtAddress;
    private TextView txtbtnChangeAddress;
    private PromoCodeHorizontalPromoScrollView hsvPromoCodes;
    private TextView txtSubTotal;
    private TextView txtPromoCode;
    private TextView txtPromoCodeAmount;
    private TextView txtTotal;
    private TextView txtUseWallet;
    private TextView txtWalletAmountDeducted;
    private TextView txtTotalAmount;
    private LinearLayout llTaxes;
    private TextView txtbtnCOD;
    private TextView txtbtnOnlinePayment;
    private Button btnProceedToPayment;
    private OAuthDetailsRequestResponse user;
    private LinearLayout llAddress;
    private ProgressBar pbAddress;
    private List<Address> addressList = new ArrayList<>();
    private int subTotal;
    private List<MenuCart> menucartitems;
    private ProgressBar pbBill;
    private LinearLayout llBill;
    private  float total, totalAmount;
    private String payment_type = "";
    private ProgressBar pbPromo;
    private EditText edtSpecialNote;

    private String promo_code = "0";

    private List<Tax> taxList = new ArrayList<>();
    private List<UserPromo> listPromoCode;
    private PromoCodeAtCheckoutAdaptor horizontalPromoAdaptor;
    private Address selectedAddress;
    private int delivery_charges = 0;
    private float tax;
    private float discount;
    private String promocodeused = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check_out);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setTitle("Checkout");
        totalAmount = 0;

        txtName = (TextView)findViewById(R.id.txtName);
        txtPhoneNumber = (TextView)findViewById(R.id.txtPhoneNumber);
        txtbtnChangePhone = (TextView)findViewById(R.id.txtbtnPhoneChange);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtbtnChangeAddress = (TextView)findViewById(R.id.txtChangeAddress);
        hsvPromoCodes = (PromoCodeHorizontalPromoScrollView) findViewById(R.id.hsvPromoCodes);
        txtSubTotal = (TextView)findViewById(R.id.txtSubTotal);
        txtPromoCode = (TextView)findViewById(R.id.txtPromoCode);
        txtPromoCodeAmount = (TextView)findViewById(R.id.txtAmountPromoCode);
        txtTotal  = (TextView)findViewById(R.id.txtTotal);
        llTaxes = (LinearLayout)findViewById(R.id.llTaxes);
        txtTotalAmount = (TextView)findViewById(R.id.txtTotalAmount);
        txtUseWallet  = (TextView)findViewById(R.id.txtbtnUseWallet);
        txtWalletAmountDeducted = (TextView)findViewById(R.id.txtWalletAmountDeducted);
        txtbtnCOD = (TextView)findViewById(R.id.txtbtnCOD);
        txtbtnOnlinePayment = (TextView)findViewById(R.id.txtbtnOnline);
        llAddress = (LinearLayout)findViewById(R.id.llAddress);
        pbAddress = (ProgressBar)findViewById(R.id.pbAddress);
        btnProceedToPayment = (Button) findViewById(R.id.btnProceed);
        pbBill = (ProgressBar)findViewById(R.id.pbBill);
        llBill = (LinearLayout)findViewById(R.id.llBill);
        pbPromo = (ProgressBar)findViewById(R.id.pbPromo);

        edtSpecialNote = (EditText)findViewById(R.id.special_note);
        txtbtnChangePhone.setOnClickListener(this);
        txtbtnChangeAddress.setOnClickListener(this);
        txtUseWallet.setOnClickListener(this);
        txtbtnCOD.setOnClickListener(this);
        txtbtnOnlinePayment.setOnClickListener(this);
        btnProceedToPayment.setOnClickListener(this);

        user= LocalDataServiceImpl.getInstance(mApp).getUser();

        if(user!=null){
            txtName.setText(user.getFname()+"  "+ user.getLname());
            txtPhoneNumber.setText(user.getAlt_phone_number());
        }

        llAddress.setVisibility(View.GONE);
        pbAddress.setVisibility(View.VISIBLE);
        llBill.setVisibility(View.GONE);
        pbBill.setVisibility(View.VISIBLE);
        pbPromo.setVisibility(View.VISIBLE);
        hsvPromoCodes.setVisibility(View.GONE);




        subTotal = 0;
        menucartitems = MenuCart.listAll(MenuCart.class);

        for(int i =0 ;i<menucartitems.size();i++){
            subTotal = subTotal + menucartitems.get(i).getMenu_price();
        }
        total = subTotal;

        if(subTotal<200){
            delivery_charges = 30;
            txtWalletAmountDeducted.setText("\u20B9"+delivery_charges);

        }


        txtSubTotal.setText("\u20B9"+subTotal);

        loadAddress();

        loadBill();

        loadPromoCode();





    }

    private void loadPromoCode() {

        pbPromo.setVisibility(View.VISIBLE);
        WebDataServiceImpl.getInstance(mApp).getUsersUnusedPromo(GET_PROMO_CHECK_OUT, UsersPromoCodeResponse.class, user.getUser_id(), this, this);

    }

    private void loadBill() {

        pbBill.setVisibility(View.VISIBLE);
        WebDataServiceImpl.getInstance(mApp).getTaxes(GET_TAXES, TaxResponse.class, null, this, this);
    }

    private void loadAddress() {

        pbAddress.setVisibility(View.VISIBLE);
        WebDataServiceImpl.getInstance(mApp).getAddress(GET_ADDRESS, AddressResponse.class, user.getUser_id(), this, this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.txtbtnPhoneChange:

                Intent intent = new Intent(this, ChangePhoneNumber.class);
                intent.putExtra("phone", user.getAlt_phone_number());
                intent.putExtra("user_id", user.getUser_id());
                startActivityForResult(intent , REQUEST_CODE);
//                startActivity(intent);

                break;
            case R.id.txtChangeAddress:

                if(addressList.size()==0){
                    Intent intent2 = new Intent(this, AddressBook_add.class);
                    startActivity(intent2);

                }else{

                    Intent intent1 = new Intent(this, CheckOutSavedAddressListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addressList", (Serializable) addressList);
                    intent1.putExtras(bundle);
                    startActivityForResult(intent1, KMNorthConstants.REQUEST_CODE_SELECT_ADDRESS);
                }

                break;
            case R.id.txtbtnUseWallet:

                break;
            case R.id.txtbtnCOD:

                payment_type = "CD";
                txtbtnCOD.setBackgroundColor(getResources().getColor(R.color.green));
                txtbtnOnlinePayment.setBackgroundColor(getResources().getColor(R.color.white));

                btnProceedToPayment.setText("Confirm Order");
                break;

            case R.id.txtbtnOnline:

//                payment_type = "OP";

                Toast.makeText(mApp, "Online Payment Gateway Comming Soon!!!", Toast.LENGTH_SHORT).show();

//                txtbtnCOD.setBackgroundColor(getResources().getColor(R.color.white));
//                txtbtnOnlinePayment.setBackgroundColor(getResources().getColor(R.color.green));
//
//                btnProceedToPayment.setText("Proceed To Payment");
                break;

            case R.id.btnProceed:


                int user_id = Integer.parseInt(user.getUser_id());
                int address_id = addressList.get(0).getUniqueId();
                //promo_code

                String special_note = edtSpecialNote.getText().toString();

//                String menu = menucartitems.toString();
                int amount_menu = subTotal;
                float amount_tax = tax;
                float amount = subTotal+tax+delivery_charges;
                float amount_discount = discount;
                float amount_wallet = delivery_charges;
                float amount_payable = totalAmount;

                Gson gsonObj = new Gson();
                // converts object to json string



                final JSONArray jsonArray=new JSONArray();

                for(int i=0;i<menucartitems.size();i++)
                {
                    JSONObject jsonObject=new JSONObject();

        //         String menu_item_det = gsonObj.toJson(menucartitems.get(i));

//                    try {
//                        jsonObject.put("menu",menu_item_det);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


//                    Log.d("MENU", menu_item_det.toString());
                    try {
                        jsonObject.put("menu_id",menucartitems.get(i).getMenu_id());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonObject.put("menu_qty",menucartitems.get(i).getMenu_qty());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonObject.put("menu_amount",menucartitems.get(i).getMenu_price());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonObject.put("menu_choice",menucartitems.get(i).getMenu_choice());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonObject.put("menu_name", menucartitems.get(i).getMenuitem().getName());
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    try {
                        jsonObject.put("menu_choice_one", menucartitems.get(i).getMenuitem().getChoice_one());
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    try {
                        jsonObject.put("menu_choice_two", menucartitems.get(i).getMenuitem().getChoice_two());
                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    try {
                        jsonObject.put("menu_description", menucartitems.get(i).getMenuitem().getDescription());
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }

                Log.d("MENu Array", jsonArray.toString());
                if(payment_type.equals("")){
                    Toast.makeText(mApp, "Select A Payment Method", Toast.LENGTH_SHORT).show();
                }else {
                    if (payment_type.equals("CD")) {
                        MenuOrderRequest menuOrderRequest = new MenuOrderRequest();
                        menuOrderRequest.setUser_id(user_id);
                        menuOrderRequest.setAddress_id(selectedAddress.getUniqueId());
                        menuOrderRequest.setPayment_type(payment_type);
                        menuOrderRequest.setPromo_code(promo_code);
                        menuOrderRequest.setMenu(jsonArray.toString());
                        menuOrderRequest.setAmount((int) amount);
                        menuOrderRequest.setAmount_discount((int) amount_discount);
                        menuOrderRequest.setAmount_menu((int) amount_menu);
                        menuOrderRequest.setAmount_payable((int) amount_payable);
                        menuOrderRequest.setAmount_tax((int) amount_tax);
                        menuOrderRequest.setAmount_wallet((int) amount_wallet);
                        menuOrderRequest.setSpecial_note_required(special_note);
                        showLoading(false);
                        WebDataServiceImpl.getInstance(mApp).bookmenu(FOOD_ORDER, MenuOrderResponse.class, menuOrderRequest, this, this);
                    } else if (payment_type.equals("OP")) {
                    }
                }



                //user_id
                //address_id
                //promo_code  0 if none
                //special_note_user

                //payment_type

                //menuitems arraylist

                //total




                break;


        }

    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GET_ADDRESS:
                    if (response.getData() instanceof AddressResponse) {

                        pbAddress.setVisibility(View.GONE);
                        llAddress.setVisibility(View.VISIBLE);
                        AddressResponse addressResponse = (AddressResponse) response.getData();
                        addressList= addressResponse.getAddress();
                        if (addressList.size() == 0) {

                            txtAddress.setText("No Address Found.");
                            txtbtnChangeAddress.setText("Add Address");

                        } else {
                            selectedAddress = addressList.get(0);
                            txtAddress.setText(addressList.get(0).getAddress() + "\n" + addressList.get(0).getInstructions()+"\n"+addressList.get(0).getDelivery_area());
                            txtbtnChangeAddress.setText("Change");
                        }
                    }
                    break;

                case GET_TAXES:
                    if(response.getData() instanceof TaxResponse){

                        pbBill.setVisibility(View.INVISIBLE);
                        llBill.setVisibility(View.VISIBLE);
                        TaxResponse taxResponse = (TaxResponse)response.getData();

                        taxList = taxResponse.getTaxes();
                        tax = 0;
                        for(int z = 0;z<taxList.size();z++){

                            llTaxes.removeAllViews();

                            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = vi.inflate(R.layout.item_tax, null);

                            TextView txtName = (TextView)v.findViewById(R.id.tax_name);
                            TextView txtPer = (TextView)v.findViewById(R.id.tax_per);

                            txtName.setText(taxList.get(z).getTax_name()+ "@"+taxList.get(z).getTax_per()+"%");

                            llTaxes.addView(v);


                            float taxap = subTotal*(taxList.get(0).getTax_per())/100;
                            tax = tax  + taxap;
                            txtPer.setText("\u20B9"+taxap + "");
                        }


                        total = total + tax;
                        txtTotal.setText("\u20B9"+total+"");
                        totalAmount = total + delivery_charges;

                        txtTotalAmount.setText("\u20B9"+totalAmount+"");



                    }
                    break;
                case GET_PROMO_CHECK_OUT:
                    if (response.getData() instanceof UsersPromoCodeResponse) {

                        pbPromo.setVisibility(View.GONE);
                        hsvPromoCodes.setVisibility(View.VISIBLE);

                        UsersPromoCodeResponse usersPromoCodeResponse = (UsersPromoCodeResponse)response.getData();
                        listPromoCode = usersPromoCodeResponse.getPromocodes();



                        horizontalPromoAdaptor = new PromoCodeAtCheckoutAdaptor(this,listPromoCode);



                        horizontalPromoAdaptor.setOnDataChangeListener(new PromoCodeAtCheckoutAdaptor.OnDataChangeListener(){
                            @Override
                            public void onDataChanged(UserPromo userPromo) {

                                if(userPromo.getPromo_code().equals(promocodeused)){
                                    Toast.makeText(mApp, "Promo Code Already In Use", Toast.LENGTH_SHORT).show();
                                }else {

                                    if(promocodeused.equals("")) {
                                        promocodeused = userPromo.getPromo_code();
                                        discount = 0;

                                        txtPromoCode.setText("Promo Code :" + userPromo.getPromo_code());
                                        promo_code = userPromo.getPromo_code();
                                        if (userPromo.isPromo_wallet()) {

                                            txtPromoCodeAmount.setText("");

                                            totalAmount = total;

                                            txtTotalAmount.setText("\u20B9" + totalAmount);

                                        } else {
                                            discount = total * userPromo.getPromo_discount_per() / 100;
                                            txtPromoCodeAmount.setText("\u20B9" + discount);


                                            total = total - discount;
                                            totalAmount = total + delivery_charges;

                                            txtTotal.setText("\u20B9" + total);

                                            txtTotalAmount.setText("\u20B9" + totalAmount);

                                        }
                                    }else{

                                        promocodeused = userPromo.getPromo_code();
                                        total = total + discount;
                                        discount = 0;
                                        if (userPromo.isPromo_wallet()) {

                                            txtPromoCodeAmount.setText("");

                                            totalAmount = total;

                                            txtTotalAmount.setText("\u20B9" + totalAmount);

                                        } else {
                                            discount = total * userPromo.getPromo_discount_per() / 100;
                                            txtPromoCodeAmount.setText("\u20B9" + discount);


                                            total = total - discount;
                                            totalAmount = total + delivery_charges;

                                            txtTotal.setText("\u20B9" + total);

                                            txtTotalAmount.setText("\u20B9" + totalAmount);

                                        }

                                    }
                                }
                            }

                        });

                        hsvPromoCodes.setAdapter(this, horizontalPromoAdaptor);

                    }
                    break;

                case FOOD_ORDER:

                    hideLoading();
                    MenuCart.deleteAll(MenuCart.class);
                    if (response.getData() instanceof MenuOrderResponse) {


                        MenuOrderResponse menuOrderResponse = (MenuOrderResponse)response.getData();

                        if(menuOrderResponse.isStatus()){

                            Order order = menuOrderResponse.getOrder();
                            Payment payment = menuOrderResponse.getPayment();

                            Intent intent = new Intent(this, TrackingOrderActivity.class);
                            Bundle bundle = new Bundle();

                            SharedPrefManager.getInstance(getApplicationContext()).storeOrderNumber(order.getOrder_id());

                            bundle.putSerializable("order", (Serializable) menuOrderResponse);
                            bundle.putSerializable("address", selectedAddress);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();


                        }else{

                            Toast.makeText(mApp, "Try Again Later", Toast.LENGTH_SHORT).show();
                        }

                    }

            }
        }
    }

    @Override
    public void onErrorResponse(VolleyResponseBean volleyResponseBean, String errorMessage) {

    }

    @Override
    public void onNetworkUnavailable(WebServiceType webServiceType) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==KMNorthConstants.REQUEST_CODE_SELECT_ADDRESS)
        {
            Bundle bundle = data.getExtras();
            selectedAddress = (Address) bundle.getSerializable("address");
            txtbtnChangeAddress.setText("Change");
            txtAddress.setText(selectedAddress.getAddress()+"\n"+selectedAddress.getInstructions()+"\n"+selectedAddress.getDelivery_area());
        }else if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            String phone_number= data.getStringExtra("phone");
            txtPhoneNumber.setText(phone_number+"");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(addressList.size() == 0){
         loadAddress();
        }
//       loadAddress();

//        loadBill();
    }


}
