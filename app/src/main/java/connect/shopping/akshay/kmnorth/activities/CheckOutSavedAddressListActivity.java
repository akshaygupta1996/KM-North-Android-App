package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.KMNorthConstants;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.adaptor.SavedAddressAdaptor;
import connect.shopping.akshay.kmnorth.bean.response.Address;

public class CheckOutSavedAddressListActivity extends KMNorthActivity implements View.OnClickListener {

    private Button btnAddAddress;
    private ListView lstSavedAddress;
    private List<Address> listAddress;
    private SavedAddressAdaptor savedAddressAdaptor;
    private Address addressSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_saved_address_list);


        setTitle("Select Address");
        final Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();

        listAddress = (ArrayList<Address>) bundle.getSerializable("addressList");

        btnAddAddress = (Button)findViewById(R.id.btnAddAddress);
        lstSavedAddress = (ListView)findViewById(R.id.lstSavedAddress);

        btnAddAddress.setOnClickListener(this);

        savedAddressAdaptor = new SavedAddressAdaptor(this, listAddress);
        lstSavedAddress.setAdapter(savedAddressAdaptor);


        savedAddressAdaptor.setOnDataChangeListener(new SavedAddressAdaptor.OnDataChangeListener() {
            @Override
            public void onDataChanged(Address address) {

                addressSelected = address;

                Intent intent1 = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("address", addressSelected);
                intent1.putExtras(bundle1);
                setResult(KMNorthConstants.REQUEST_CODE_SELECT_ADDRESS, intent1);
                finish();

            }
        });


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnAddAddress:

                Intent intent = new Intent(getApplicationContext(), AddressBook_add.class);
                startActivity(intent);
                break;

        }



    }

    @Override
    public void onBackPressed() {

        Toast.makeText(mApp, "Select an Address", Toast.LENGTH_SHORT).show();
//        super.onBackPressed();
//
//        finish();

    }
}
