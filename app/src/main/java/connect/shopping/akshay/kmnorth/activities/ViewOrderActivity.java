package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.adaptor.ViewMenuAdaptor;
import connect.shopping.akshay.kmnorth.bean.local.MenuItemOrder;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;

public class ViewOrderActivity extends AppCompatActivity {

    private ListView lvMenuItems;
    private ViewMenuAdaptor viewMenuAdaptor;
    private List<MenuItemOrder> listItems;
    private MenuOrderResponse menuOrderResponse;
    private TextView txtSubtotal;
    private TextView txtPromoCode;
    private TextView txtPromoCodeDiscount;
    private TextView txtTaxes;
    private TextView txtDeliveryCharges;
    private TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);


        setTitle("Order Details");


        txtSubtotal = (TextView)findViewById(R.id.txtSubTotal);
        txtPromoCode = (TextView)findViewById(R.id.txtPromoCode);
        txtPromoCodeDiscount = (TextView)findViewById(R.id.txtAmountPromoCode);
        txtTaxes = (TextView)findViewById(R.id.txtTaxesAmount);
        txtDeliveryCharges = (TextView)findViewById(R.id.txtWalletAmountDeducted);
        txtTotalAmount = (TextView)findViewById(R.id.txtTotalAmount);


        lvMenuItems = (ListView)findViewById(R.id.lvViewOrder);

        Bundle bundle = getIntent().getExtras();

        menuOrderResponse = (MenuOrderResponse) bundle.getSerializable("menuorder");
        listItems = menuOrderResponse.getMenu();

        viewMenuAdaptor = new ViewMenuAdaptor(this, listItems);
        lvMenuItems.setAdapter(viewMenuAdaptor);

        txtSubtotal.setText("\u20B9 "+ menuOrderResponse.getPayment().getAmount_menu());
        txtTaxes.setText("\u20B9 "+menuOrderResponse.getPayment().getAmount_tax());
        txtDeliveryCharges.setText("\u20B9 "+menuOrderResponse.getPayment().getAmount_wallet());
        txtTotalAmount.setText("\u20B9 "+menuOrderResponse.getPayment().getAmount_payable());
        if(menuOrderResponse.getOrder().getPromo_code()!="0"){
            txtPromoCode.setText(menuOrderResponse.getOrder().getPromo_code());
            txtPromoCodeDiscount.setText("- \u20B9 "+menuOrderResponse.getPayment().getAmount_discount());
        }
        else{
            txtPromoCode.setText("No Promo Code Applied");
            txtPromoCodeDiscount.setText("");
        }



    }
}
