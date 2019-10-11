package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.LogUtils;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.SharedPrefManager;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.response.LoginResponse;

public class LoginActivity extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {


    private EditText edtLoginEmailPhone;
    private EditText edtLoginPassword;
    private TextView textbtnForgetPassword;
    private Button btnSignIn;
    private Button textbtnSignUp;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtLoginEmailPhone = (EditText)findViewById(R.id.edtLoginEmailPhone);
        edtLoginPassword = (EditText)findViewById(R.id.edtLoginPassword);
        textbtnForgetPassword = (TextView)findViewById(R.id.textbtnForgetPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        textbtnSignUp = (Button)findViewById(R.id.textbtnSignUp);
        textbtnForgetPassword.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        textbtnSignUp.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnSignIn:
                login();
                break;
            case R.id.textbtnForgetPassword:
                Intent intent1 = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.textbtnSignUp:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private void login() {

        String emailPhone = edtLoginEmailPhone.getText().toString();
        String password = edtLoginPassword.getText().toString();

        int flag = 0;
        boolean valid = validate(emailPhone, password);
        if(emailPhone.length() == 10 && emailPhone.matches("[0-9]+")){
            flag = 1;
        }else{
            flag = 0;
        }
        if(valid){

            if (SharedPrefManager.getInstance(this).getToken() != null) {

                String fcmtoken = SharedPrefManager.getInstance(this).getToken();
                showLoading(false);
                WebDataServiceImpl.getInstance(mApp).generateToken(LoginResponse.class, emailPhone, password,flag,fcmtoken, this, this);

            } else {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                if(refreshedToken!=null){
                    showLoading(false);
                    WebDataServiceImpl.getInstance(mApp).generateToken(LoginResponse.class, emailPhone, password,flag,refreshedToken, this, this);
                }else {
                    Toast.makeText(mApp, "FCM Token not generated yet!!!   " + refreshedToken, Toast.LENGTH_SHORT).show();
                }
            }




//            showLoading(false);
//            OAuthDetailsRequestResponse loginRequest = new OAuthDetailsRequestResponse(OAuthGrantType.PASSWORD.getGrantTypeValue(), mActivity.getResources().getString(R.string.client_id), mActivity.getResources().getString(R.string.client_secret), userName, password);

//            OAuthDetailsRequestResponse loginRequest = new OAuthDetailsRequestResponse(emailPhone, password);
//            WebDataServiceImpl.getInstance(mApp).generateToken(LoginResponse.class, emailPhone, password,flag, this, this);


        }









    }

    private boolean validate(String emailPhone, String password){

        boolean valid = true;
        if(emailPhone.length()==10&&emailPhone.matches("[0-9]+")){

            edtLoginEmailPhone.setError(null);

        }else{
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailPhone).matches()){

                edtLoginEmailPhone.setError(null);
            }else{
                edtLoginEmailPhone.setError("Enter Valid Email Or Phone Number");
                valid = false;
            }
        }

        if(password!=null){

            edtLoginPassword.setError(null);
        }else{
            valid = false;
            edtLoginPassword.setError("Password cannot be empty");
        }

        return valid;


    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GENERATE_ACCESS_TOKEN:
                    if (response.getData() instanceof LoginResponse) {

                        LoginResponse loginResponse = (LoginResponse)response.getData();
                        if(loginResponse.isStatus()==true){
                            OAuthDetailsRequestResponse auth = loginResponse.getUser();
                            LocalDataServiceImpl.getInstance(mApp).addOAuthToken(auth);
                            LogUtils.LOGD(TAG, "Success");

                            String auth_token = auth.getCustom_token();

//                            ZendeskConfig.INSTANCE.init(this, "https://kmnorth.zendesk.com", "b55df6b5b344c66a7c5deef9a877bd54399ae4595db11dda", "mobile_sdk_client_75e4f39118d5b67349f2");
//                            Identity identity = new AnonymousIdentity.Builder()
//                                    .withEmailIdentifier(loginResponse.getUser().getEmail())
//                                    .build();
//                            ZendeskConfig.INSTANCE.setIdentity(identity);

                            mAuth.signInWithCustomToken(auth_token)
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d(TAG, "signInWithCustomToken:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                Intent intent = new Intent(LoginActivity.this, Navigation_drawer.class);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
//                                                updateUI(null);
                                            }
                                        }
                                    });
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        }else{

                            Toast.makeText(mApp, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                            edtLoginPassword.setText("");
                            edtLoginEmailPhone.setText("");
                        }

//                        WebDataServiceImpl.getInstance(mApp).getOtp(Boolean.class, Util.getValidatedValueOrNull(editMobileNo), this, this);
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
