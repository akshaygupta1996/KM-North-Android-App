package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.response.Simple;

public class ChangePasswordActivity extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {


    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private Button btnSavePasswor;
    private OAuthDetailsRequestResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        setTitle("Change Password");
        user = LocalDataServiceImpl.getInstance(mApp).getUser();


        edtOldPassword = (EditText) findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtReNewPassword);

        btnSavePasswor = (Button) findViewById(R.id.btnSavePassword);
        btnSavePasswor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.btnSavePassword:


                String oldpass = edtOldPassword.getText().toString();
                String newPass = edtNewPassword.getText().toString();
                String confirmPass = edtConfirmPassword.getText().toString();

                if (newPass.length() <= 8) {
                    edtNewPassword.setError("Password must be atleast 8 characters");
                } else {

                    if (newPass.equals(confirmPass)) {

                        showLoading(false);
                        WebDataServiceImpl.getInstance(mApp).changePassword(WebServiceType.CHANGE_PASSWORD,Simple.class,user.getUser_id(), oldpass, newPass, this, this);



                    } else {

                        edtConfirmPassword.setError("Password must match");

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
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case CHANGE_PASSWORD:
                    if (response.getData() instanceof Simple) {

                        Simple simple = (Simple) response.getData();
                        if(simple.getStatus()){

                            Toast.makeText(mApp, "Password Changed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mApp, "Old Password Incorrect.. Try Again", Toast.LENGTH_SHORT).show();
                        }

                        edtConfirmPassword.setText("");
                        edtNewPassword.setText("");
                        edtOldPassword.setText("");
                    }
            }
        }
        

    }
}
