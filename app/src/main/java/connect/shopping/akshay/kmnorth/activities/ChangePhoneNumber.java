package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
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
import connect.shopping.akshay.kmnorth.bean.response.ChangePhoneNumberResponse;
import connect.shopping.akshay.kmnorth.bean.response.SendOtpResponse;

import static connect.shopping.akshay.kmnorth.WebServiceType.CHANGE_PHONE_NUMBER;

public class ChangePhoneNumber extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener{


    private EditText edtChangePhoneNumber;
    private Button btnVerifyPhoneNumber;
    private String phone_number;
    private String user_id;
    private AppCompatButton buttonConfirm;
    private EditText editTextConfirmOtp;
    private SendOtpResponse otpResponse;
    private AlertDialog alertDialog;
    private TextView txtOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);


        phone_number = getIntent().getStringExtra("phone");
        user_id = getIntent().getStringExtra("user_id");


        edtChangePhoneNumber = (EditText)findViewById(R.id.edtChangePhoneNumber);
        btnVerifyPhoneNumber = (Button)findViewById(R.id.btnVerifyPhoneNumber);


        edtChangePhoneNumber.setHint(phone_number);

        btnVerifyPhoneNumber.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnVerifyPhoneNumber:

                phone_number = edtChangePhoneNumber.getText().toString();

//                showLoading(false);
//                WebDataServiceImpl.getInstance(mApp).changePhoneNumber(CHANGE_PHONE_NUMBER, ChangePhoneNumberResponse.class,phone_number, Integer.parseInt(user_id), this, this);


                 sendotp(phone_number);

                break;

            case R.id.buttonConfirm:

                String otp = editTextConfirmOtp.getText().toString();
                if(otp.length()!=4){
                    editTextConfirmOtp.setError("Enter 4 digit valid OTP");
                }else {
                    if (otp.equals(otpResponse.getOtp()+"")) {

                        txtOtp.setText("OTP Verified...Registering User");
                        showLoading(false);
                        WebDataServiceImpl.getInstance(mApp).changePhoneNumber(CHANGE_PHONE_NUMBER, ChangePhoneNumberResponse.class,phone_number, Integer.parseInt(user_id), this, this);


                    } else {

                        txtOtp.setText("Invalid OTP ..Try Again");

                    }
                }

        }
    }

    private void sendotp(String phone_number){

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
        WebDataServiceImpl.getInstance(mApp).sendotp(WebServiceType.GENERATE_OTP, SendOtpResponse.class, phone_number, this, this);









    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case CHANGE_PHONE_NUMBER:
                    if (response.getData() instanceof ChangePhoneNumberResponse) {

                        ChangePhoneNumberResponse response1 = (ChangePhoneNumberResponse) response.getData();

                        if(response1.isStatus()){

                            Intent intent = getIntent();
                            intent.putExtra("phone",response1.getUser().getAlt_phone_number());
                            setResult(RESULT_OK, intent);
                            finish();
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
