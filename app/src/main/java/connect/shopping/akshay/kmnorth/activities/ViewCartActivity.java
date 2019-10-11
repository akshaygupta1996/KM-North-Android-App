package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.local.MenuItemOrder;

public class ViewCartActivity extends AppCompatActivity {


    private List<MenuItemOrder> menuitems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
    }
}
