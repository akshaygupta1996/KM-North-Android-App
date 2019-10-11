package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.request.AddAddressRequest;
import connect.shopping.akshay.kmnorth.bean.response.AddAddressResponse;
import connect.shopping.akshay.kmnorth.bean.response.Address;

public class AddCompleteAddress extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {
    private static final String SUBLOCALITY = "SUBLOCALITY";
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String LOCALITY = "LOCALITY";
    String locality;
    String sublocality;
    Button btn_chng, btn_save;
    EditText del_area;
    EditText complete_area;
    EditText Instruction;
    private OAuthDetailsRequestResponse user;
    private Address address;
    private boolean isEditAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complete_address);
        btn_chng = (Button) findViewById(R.id.btn_chng);
        btn_save = (Button) findViewById(R.id.saver);
        del_area = (EditText) findViewById(R.id.del_area);
        complete_area = (EditText) findViewById(R.id.edt_complete_add);
        Instruction = (EditText) findViewById(R.id.edt_instruction);

//        del_area.setEnabled(false);


       // Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);


        btn_save.setOnClickListener(this);

        if (getIntent().getExtras() != null) {

            final Intent intent = this.getIntent();
            final Bundle bundle = intent.getExtras();


            isEditAddress = bundle.getBoolean("editaddress");

            if(isEditAddress){


                address = (Address) bundle.getSerializable("address");
                complete_area.setText(address.getAddress());
                Instruction.setText(address.getInstructions());
                del_area.setText(address.getDelivery_area());
            }else{

                locality = bundle.getString(LOCALITY);
                sublocality = bundle.getString(SUBLOCALITY);
                del_area.setText(sublocality);

            }

        } else {
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.saver:

                showLoading(false);

                user = LocalDataServiceImpl.getInstance(mApp).getUser();

                if (isEditAddress == true) {

                    AddAddressRequest addAddressRequest = new AddAddressRequest(complete_area.getText().toString(), del_area.getText().toString(), Instruction.getText().toString());
                    WebDataServiceImpl.getInstance(mApp).editAddress(WebServiceType.EDIT_ADDRESS, AddAddressResponse.class, addAddressRequest, address.getUniqueId(), this, this);


                } else {

                    if (complete_area.getText().toString() == "") {
                        complete_area.setError("Address Required");
                    } else {


                        AddAddressRequest addAddressRequest = new AddAddressRequest(complete_area.getText().toString(), del_area.getText().toString(), Instruction.getText().toString());
                        WebDataServiceImpl.getInstance(mApp).addUserAddress(WebServiceType.ADD_ADDRESS, AddAddressResponse.class, addAddressRequest, user.getUser_id(), this, this);

                    }
                }


                break;

            case R.id.btn_chng:

//                del_area.setEnabled(true);

                break;
        }

    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case ADD_ADDRESS:
                    if (response.getData() instanceof AddAddressResponse) {

                        AddAddressResponse addAddressResponse = (AddAddressResponse) response.getData();
                        if (addAddressResponse.isStatus()) {

                            List<Address> addressList = new ArrayList<>();
                            addressList = addAddressResponse.getAddress();

                            Intent intent = new Intent(this, SavedAddress_list.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("addessesList", (Serializable) addressList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();


                        } else {
                            Toast.makeText(mApp, "Error Adding Address.. Try Again Later", Toast.LENGTH_SHORT).show();
                        }


                    }
                    break;

                case EDIT_ADDRESS:
                    if (response.getData() instanceof AddAddressResponse) {

                        AddAddressResponse addAddressResponse = (AddAddressResponse) response.getData();
                        if (addAddressResponse.isStatus()) {

                            List<Address> addressList = new ArrayList<>();
                            addressList = addAddressResponse.getAddress();

                            Intent intent = new Intent(this, SavedAddress_list.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("addessesList", (Serializable) addressList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();


                        } else {
                            Toast.makeText(mApp, "Error Adding Address.. Try Again Later", Toast.LENGTH_SHORT).show();
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
}
