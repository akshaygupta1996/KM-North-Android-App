package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.request.RegisterRequest;
import connect.shopping.akshay.kmnorth.bean.response.RegisterResponse;
import connect.shopping.akshay.kmnorth.bean.response.SendOtpResponse;

public class RegisterActivity extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {

    private EditText edtFname;
    private EditText edtLname;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private EditText edtPassword;
    private EditText edtRePassword;
    private EditText edtReferCode;
    private Button btnRegister;
    private Button txtBtnSignIn;
    private AppCompatButton buttonConfirm;
    private EditText editTextConfirmOtp;
    private SendOtpResponse otpResponse;
    private AlertDialog alertDialog;
    private TextView txtOtp;
    private RegisterRequest registerRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edtFname = (EditText)findViewById(R.id.edtRegisterFname);
        edtLname = (EditText)findViewById(R.id.edtRegisterLname);
        edtEmail = (EditText)findViewById(R.id.edtRegisterEmail);
        edtPhoneNumber = (EditText)findViewById(R.id.edtRegisterPhone);
        edtPassword = (EditText)findViewById(R.id.edtRegisterPassword);
        edtRePassword = (EditText)findViewById(R.id.edtRegisterRPassword);
        edtReferCode = (EditText)findViewById(R.id.edtReferCode);
        txtBtnSignIn = (Button)findViewById(R.id.textbtnSignIn);

        txtBtnSignIn.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);






        btnRegister = (Button)findViewById(R.id.btnSignUp);
        btnRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSignUp:
                String fname = edtFname.getText().toString();
                String lname = edtLname.getText().toString();
                String email = edtEmail.getText().toString();
                String phone_number = edtPhoneNumber.getText().toString();
                String password = edtPassword.getText().toString();
                String repassword = edtRePassword.getText().toString();
                String referCode = edtReferCode.getText().toString();
                if(referCode.equals("")){
                    referCode = "0";
                }
                boolean validity = validate();
                if(validity){
//                    showLoading(false);
                    registerRequest = new RegisterRequest(email, fname, lname, password, phone_number, referCode);

                    sendotp(registerRequest);
//                    WebDataServiceImpl.getInstance(mApp).registerUser(WebServiceType.REGISTER_USER, RegisterResponse.class, registerRequest, this, this);


                }
                break;
            case R.id.textbtnSignIn:

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buttonConfirm:

                String otp = editTextConfirmOtp.getText().toString();
                if(otp.length()!=4){
                    editTextConfirmOtp.setError("Enter 4 digit valid OTP");
                }else {
                    if (otp.equals(otpResponse.getOtp()+"")) {

                        txtOtp.setText("OTP Verified...Registering User");
                        showLoading(false);
                        WebDataServiceImpl.getInstance(mApp).registerUser(WebServiceType.REGISTER_USER, RegisterResponse.class, registerRequest, this, this);


                    } else {

                        txtOtp.setText("Invalid OTP ..Try Again");

                    }
                }


        }

    }


    public void sendotp(RegisterRequest request){

        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.generate_otp_dialog, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(this);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        txtOtp = (TextView)confirmDialog.findViewById(R.id.txtOtp);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

//        buttonConfirm.setClickable(false);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
         alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        showLoading(false);
        WebDataServiceImpl.getInstance(mApp).sendotp(WebServiceType.GENERATE_OTP, SendOtpResponse.class, request.getPhone_number(), this, this);







    }

    private boolean validate() {



        boolean valid = true;

        String v_fname = edtFname.getText().toString();
        String v_lname=edtLname.getText().toString();
        String v_email = edtEmail.getText().toString();
        String v_password = edtPassword.getText().toString();
        String v_repassword=edtRePassword.getText().toString();
        String v_mobile=edtPhoneNumber.getText().toString();


        if (v_fname.isEmpty() || v_fname.length() < 3) {
            edtFname.setError("at least 3 characters");
            valid = false;
        } else {
            edtFname.setError(null);
        }
        if (v_lname.isEmpty() || v_lname.length() < 3) {
            edtLname.setError("at least 3 characters");
            valid = false;
        } else {
            edtLname.setError(null);
        }

        if (v_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(v_email).matches()) {
            edtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }
        if (v_password.isEmpty() || v_password.length() < 4 || v_password.length() > 10) {
            edtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }
        if (!v_repassword.equals(v_password)) {
            edtRePassword.setError("Both password should match");
            valid = false;
        } else {
            edtRePassword.setError(null);
        }
        if (v_mobile.isEmpty() || !Patterns.PHONE.matcher(v_mobile).matches() ||v_mobile.length()!=10) {
            edtPhoneNumber.setError("Phone Number not valid");
            valid = false;
        } else {
            edtPhoneNumber.setError(null);
        }


        return valid;
    }

    @Override
    public void onResponse(VolleyResponseBean response) {

        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case REGISTER_USER:
                    if (response.getData() instanceof RegisterResponse) {

                        alertDialog.dismiss();
                        RegisterResponse registerResponse = (RegisterResponse)response.getData();
                        boolean status = registerResponse.isStatus();
                        if(status==true){
                            Log.d("USER","USER Registered");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            String mes = registerResponse.getMessage();
                            if(mes.contains("Email")){
                                edtEmail.setError(mes);
                                edtEmail.setText("");
                                edtPassword.setText("");
                                edtRePassword.setText("");
                            }else{
                                edtPhoneNumber.setError(mes);
                                edtPhoneNumber.setText("");
                                edtPassword.setText("");
                                edtRePassword.setText("");
                            }
                        }

                    }

                    break;

                case GENERATE_OTP:

                    hideLoading();
                    if (response.getData() instanceof SendOtpResponse) {

                        otpResponse = (SendOtpResponse)response.getData();
                        if(otpResponse.getRes().getError()==null){

//                            buttonConfirm.setClickable(true);
                        }else{
                            Toast.makeText(mApp, "Error Sending OTP.. Try Again Later", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
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
