package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;

public class ProfileActivity extends KMNorthActivity implements View.OnClickListener {


    private TextView txtName;
    private TextView txtMobile;
    private TextView txtEmail;
    private TextView txtReference;
    private Button btnChangePassword;
    private OAuthDetailsRequestResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile");


        txtName = (TextView)findViewById(R.id.txtName);
        txtMobile = (TextView)findViewById(R.id.txtMobile);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtReference = (TextView)findViewById(R.id.txtRefCode);
        btnChangePassword = (Button)findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(this);



        user= LocalDataServiceImpl.getInstance(mApp).getUser();

        txtName.setText(user.getFname()+"  "+user.getLname());
        txtMobile.setText(user.getPhone_number());
        txtEmail.setText(user.getEmail());


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnChangePassword:

                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);

        }

    }
}
