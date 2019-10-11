package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.response.ForgetPasswordResponse;

public class ForgetPasswordActivity extends KMNorthActivity implements View.OnClickListener,  Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {


    private Button btnResetPassword;
    private EditText edtPhoneNumber;
    private String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        btnResetPassword = (Button)findViewById(R.id.btnResetPassword);
        edtPhoneNumber = (EditText)findViewById(R.id.edtPhoneNumber);

        btnResetPassword.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){


            case R.id.btnResetPassword:

                phone_number = edtPhoneNumber.getText().toString();

                if(phone_number.equals("")||phone_number.length()!=10){
                    edtPhoneNumber.setError("Enter Valid Phone Number");
                }else{

                    showLoading(false);
                    WebDataServiceImpl.getInstance(mApp).forgetPassword(WebServiceType.FORGET_PASSWORD, ForgetPasswordResponse.class, phone_number, this, this);


                }


                break;

        }
    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case FORGET_PASSWORD:
                    if (response.getData() instanceof ForgetPasswordResponse) {

                        ForgetPasswordResponse response1 = (ForgetPasswordResponse) response.getData();

                        if (response1.isStatus()) {

                            finish();
                            Toast.makeText(mApp, "Password will be sent to your phone Number Shortly...", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(mApp, "No user Found..", Toast.LENGTH_SHORT).show();

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
}
