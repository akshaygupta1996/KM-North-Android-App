package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import connect.shopping.akshay.kmnorth.adaptor.OrderHistoryAdaptor;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;
import connect.shopping.akshay.kmnorth.bean.response.OrderHistoryResponse;

import static connect.shopping.akshay.kmnorth.WebServiceType.GET_ORDER_HISTORY;

public class  OrderHistoryActivity extends KMNorthActivity implements Response.Listener<VolleyResponseBean>, GsonRequest.ErrorListener {

    private ListView lvOrderHistory;
    private List<MenuOrderResponse> listMenuOrderResponse;
    private OrderHistoryResponse orderHistoryResponse;
    private OrderHistoryAdaptor orderHistoryAdaptor;
    private OAuthDetailsRequestResponse user;
    private ImageView imgInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        imgInternet = (ImageView)findViewById(R.id.imgInternet);

        setTitle("Order History");


        lvOrderHistory = (ListView)findViewById(R.id.lvOrderHistory);


        imgInternet.setVisibility(View.GONE);
        lvOrderHistory.setVisibility(View.VISIBLE);
        listMenuOrderResponse = new ArrayList<>();
        getData();

    }

    private void getData() {

        showLoading(false);

        user= LocalDataServiceImpl.getInstance(mApp).getUser();
        WebDataServiceImpl.getInstance(mApp).getOrderHistory(GET_ORDER_HISTORY, OrderHistoryResponse.class, user.getUser_id(), this, this);

    }

    @Override
    public void onResponse(VolleyResponseBean response) {

        hideLoading();
        if (response.isValidData(response)) {
            switch (response.getWebServiceType()) {
                case GET_ORDER_HISTORY:
                    if (response.getData() instanceof OrderHistoryResponse) {

                        OrderHistoryResponse orderHistoryResponse = (OrderHistoryResponse)response.getData();
                        listMenuOrderResponse = orderHistoryResponse.getMenu();
                        orderHistoryAdaptor = new OrderHistoryAdaptor(this, listMenuOrderResponse);
                        lvOrderHistory.setAdapter(orderHistoryAdaptor);
                    }
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyResponseBean volleyResponseBean, String errorMessage) {

        if(!volleyResponseBean.isUserOnline()){
            imgInternet.setVisibility(View.VISIBLE);
            lvOrderHistory.setVisibility(View.GONE);

        }

    }

    @Override
    public void onNetworkUnavailable(WebServiceType webServiceType) {

        if(webServiceType==WebServiceType.GET_ORDER_HISTORY) {

            imgInternet.setVisibility(View.VISIBLE);
            lvOrderHistory.setVisibility(View.GONE);
        }

    }
}
