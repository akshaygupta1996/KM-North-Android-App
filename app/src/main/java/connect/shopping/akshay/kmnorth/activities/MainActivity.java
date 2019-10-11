package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;

import java.io.Serializable;
import java.util.List;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.bean.response.ArrayMenuResponse;
import connect.shopping.akshay.kmnorth.bean.response.MenuResponse;

public class MainActivity extends KMNorthActivity implements View.OnClickListener, Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener  {

    ImageButton  orderfood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderfood = (ImageButton)findViewById(R.id.order_food);

        orderfood.setOnClickListener(this);
//        orderfood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this,Navigation_drawer.class);
//                startActivity(i);
//            }
//        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.order_food:

                showLoading(false);

                WebDataServiceImpl.getInstance(mApp).getMenuList(WebServiceType.GET_ALL_MENU_ITEM, MenuResponse.class, null,this, this);


                break;
        }
    }

    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GET_ALL_MENU_ITEM:
                    if (response.getData() instanceof MenuResponse) {

                        MenuResponse response1 = (MenuResponse) response.getData();
                        if(response1.isStatus()){

                            List<ArrayMenuResponse> menuResponse = (List<ArrayMenuResponse>) response1.getMenu();

                            Intent i = new Intent(getApplicationContext(),Navigation_drawer.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("menu", (Serializable) menuResponse);
                            i.putExtras(bundle);
                            startActivity(i);


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

    }

    @Override
    public void onNetworkUnavailable(WebServiceType webServiceType) {

    }
}
