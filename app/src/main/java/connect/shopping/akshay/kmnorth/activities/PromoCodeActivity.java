package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.GsonRequest;
import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.VolleyResponseBean;
import connect.shopping.akshay.kmnorth.WebDataServiceImpl;
import connect.shopping.akshay.kmnorth.WebServiceType;
import connect.shopping.akshay.kmnorth.adaptor.PromoCodeAdaptor;
import connect.shopping.akshay.kmnorth.bean.other.UserPromo;
import connect.shopping.akshay.kmnorth.bean.response.UsersPromoCodeResponse;

import static connect.shopping.akshay.kmnorth.WebServiceType.GET_USERS_PROMO;

public class PromoCodeActivity extends KMNorthActivity implements Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener{

    private ListView lstPromoCode;
    private PromoCodeAdaptor promoCodeAdaptor;
    private List<UserPromo> listPromoCode;
    private OAuthDetailsRequestResponse user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);

        setTitle("Promo Code");


        listPromoCode = new ArrayList<>();
        lstPromoCode = (ListView)findViewById(R.id.lstPromoCode);

        getData();
    }

    private void getData() {


        showLoading(false);

        user= LocalDataServiceImpl.getInstance(mApp).getUser();
        WebDataServiceImpl.getInstance(mApp).getUsersUnusedPromo(GET_USERS_PROMO, UsersPromoCodeResponse.class, user.getUser_id(), this, this);

    }


    @Override
    public void onResponse(VolleyResponseBean response) {
        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GET_USERS_PROMO:
                    if (response.getData() instanceof UsersPromoCodeResponse) {

                        UsersPromoCodeResponse usersPromoCodeResponse = (UsersPromoCodeResponse)response.getData();
                        listPromoCode = usersPromoCodeResponse.getPromocodes();
                        promoCodeAdaptor = new PromoCodeAdaptor(this, listPromoCode);
                        lstPromoCode.setAdapter(promoCodeAdaptor);

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
