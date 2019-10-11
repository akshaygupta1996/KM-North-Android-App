package connect.shopping.akshay.kmnorth.activities;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;
import connect.shopping.akshay.kmnorth.bean.other.MenuItem;

public class MenuPopUpActivity extends KMNorthActivity implements View.OnClickListener{


    private ImageView imageMenu;
    private TextView txtPMenuName;
    private TextView txtPMenuDescription;
    private TextView txtPFullPrice;
    private TextView txtPHalfPrice;
    private TextView txtPQtyBtn;
    private Button btnPSubMenu;
    private Button btnPAddMenu;
    private RadioGroup rgMenuChoice;
    private RadioButton rbMenuChoiceOne;
    private RadioButton rbMenuChoiceTwo;
    private TextView txtPMenuQty;
    private TextView txtPMenuPrice;
    private Button btnAddToCart;
    private CardView cvChoice;

    private MenuItem menuItem;
    private int selectedChoice = 1;
    private boolean half = false;
    private float selectedQty = 0;
    private int menuPrice = 0;
    private ArrayList<MenuCart> menu;
    private ImageButton imgbtnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pop_up);


        imageMenu = (ImageView)findViewById(R.id.menuImage);
        txtPMenuName = (TextView) findViewById(R.id.txtPMenuName);
        txtPMenuDescription = (TextView)findViewById(R.id.txtPMenuDescription);
        txtPFullPrice = (TextView)findViewById(R.id.txtPMenuFullPrice);
        txtPHalfPrice = (TextView)findViewById(R.id.txtPMenuHalfPrice);
        btnPAddMenu = (Button)findViewById(R.id.btnPAddMenu);
        btnPSubMenu = (Button)findViewById(R.id.btnPSubMenu);
        txtPQtyBtn = (TextView)findViewById(R.id.txtPQtybtn);
        imgbtnDelete = (ImageButton)findViewById(R.id.imgbtnDelete);

        imgbtnDelete.setVisibility(View.INVISIBLE);
        imgbtnDelete.setOnClickListener(this);

        rgMenuChoice = (RadioGroup)findViewById(R.id.rgPMenuChoice);
        rbMenuChoiceOne = (RadioButton)findViewById(R.id.rbPChoiceOne);
        rbMenuChoiceTwo = (RadioButton)findViewById(R.id.rbPChoiceTwo);

        txtPMenuQty = (TextView)findViewById(R.id.txtPMenuQty);

        txtPMenuPrice = (TextView)findViewById(R.id.txtPMenuPrice);
        btnAddToCart = (Button)findViewById(R.id.btnAddToCart);
        cvChoice = (CardView)findViewById(R.id.cvChoice);

        btnPAddMenu.setOnClickListener(this);
        btnPSubMenu.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        menuItem = (MenuItem) bundle.getSerializable("menuitem");

        menu = (ArrayList<MenuCart>) MenuCart.findWithQuery(MenuCart.class, "Select * from menu_cart where menuid = ?", menuItem.getUniqueId()+"");



        txtPMenuName.setText(menuItem.getName());
        txtPMenuDescription.setText(menuItem.getDescription());
        txtPMenuQty.setText("0");
//        txtPMenuQty.setText("");
        txtPMenuPrice.setText("");

        setTitle(menuItem.getName());


        Glide.with(this).load("http://res.cloudinary.com/kmnorth/image/upload/v1503858950/"+menuItem.getUniqueId()+".jpg").into(imageMenu);


        txtPFullPrice.setText("₹"+menuItem.getFull_price()+"");

        if(menuItem.getHalf_price()==0){
            txtPHalfPrice.setText("");

            half = false;
            txtPQtyBtn.setText("0");
        }else{
            txtPHalfPrice.setText("/    "+menuItem.getHalf_price());
            txtPQtyBtn.setText("0");
            half = true;
        }




        if(menuItem.isChoice()){

            cvChoice.setVisibility(View.VISIBLE);
            rbMenuChoiceOne.setText(menuItem.getChoice_one());
            rbMenuChoiceTwo.setText(menuItem.getChoice_two());
            rbMenuChoiceOne.setChecked(true);
        }else{
            cvChoice.setVisibility(View.GONE);
        }

        rgMenuChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbPChoiceOne){
                    selectedChoice = 1;
                }else if(i == R.id.rbPChoiceTwo){
                    selectedChoice = 2;
                }
            }
        });


        if(menu.size()>0){

            imgbtnDelete.setVisibility(View.VISIBLE);

            selectedQty = menu.get(0).getMenu_qty();
            selectedChoice = menu.get(0).getMenu_choice();

            menuPrice = menu.get(0).getMenu_price();

            if(selectedQty == (int)selectedQty){

                txtPQtyBtn.setText((int)selectedQty+"");
                txtPMenuQty.setText((int)selectedQty+"");
            }else{
                txtPQtyBtn.setText(selectedQty+"");
                txtPMenuQty.setText(selectedQty+"");
            }
            txtPMenuPrice.setText(menuPrice+"");

        }






    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnPAddMenu:

                if(selectedQty >=10){

                    Toast.makeText(mApp, "No More Items Cannot Be Added", Toast.LENGTH_SHORT).show();
                }else {
                    if (half == true) {
                        selectedQty = (float) (selectedQty + 0.5);
                        if(selectedQty==(int)selectedQty){
                            menuPrice = (int) (menuItem.getFull_price()*selectedQty);
                        }else{
                            int qty = (int)selectedQty;
                            menuPrice = (int) (menuItem.getFull_price()*qty);
                            menuPrice = menuPrice + menuItem.getHalf_price();
                        }
                    } else {
                        selectedQty = selectedQty + 1;
                        menuPrice = menuPrice + menuItem.getFull_price();
                        selectedQty = (int)selectedQty;
                    }
                }

                if(selectedQty == (int)selectedQty){

                    txtPQtyBtn.setText((int)selectedQty+"");
                    txtPMenuQty.setText((int)selectedQty+"");
                }else{
                    txtPQtyBtn.setText(selectedQty+"");
                    txtPMenuQty.setText(selectedQty+"");
                }

                txtPMenuPrice.setText("\u20B9"+menuPrice+"");
                break;

            case R.id.btnPSubMenu:

                if(selectedQty==0){

                }else{
                    if(half){
                        selectedQty = (float) (selectedQty - 0.5);
                        if(selectedQty == (int)(selectedQty)){
                            menuPrice = (int) (menuItem.getFull_price()*selectedQty);
                        }else{
                            int qty = (int)selectedQty;
                            menuPrice = (int) (menuItem.getFull_price()*qty);
                            menuPrice = menuPrice + menuItem.getHalf_price();
                        }

                    }else{
                        selectedQty = selectedQty - 1;
                        menuPrice = menuPrice - menuItem.getFull_price();
                        selectedQty = (int)selectedQty;
                    }
                }

                if(selectedQty == (int)selectedQty){

                    txtPQtyBtn.setText((int)selectedQty+"");
                    txtPMenuQty.setText((int)selectedQty+"");
                }else{
                    txtPQtyBtn.setText(selectedQty+"");
                    txtPMenuQty.setText(selectedQty+"");
                }
                txtPMenuPrice.setText("\u20B9"+menuPrice+"");
                break;

            case R.id.btnAddToCart:

                if(selectedQty==0){
                    Toast.makeText(mApp, "Quantity Not Selected", Toast.LENGTH_SHORT).show();
                }else{


                    if(menu.size()==0) {

                        MenuCart menuCart;
                        MenuItem menuItem1;

                        menuItem1 = menuItem;
                        LocalDataServiceImpl.getInstance(mApp).addMenuItem(menuItem1);

                        if (menuItem.isChoice()) {
                            if (selectedChoice == 1) {
                                menuCart = new MenuCart(menuItem.getUniqueId(), menuItem.getChoice_one(), selectedChoice, menuPrice, selectedQty, menuItem1);
//                             menuCart = new MenuCart(menuItem.getUniqueId(), menuItem.getName(), menuPrice, selectedQty, selectedChoice,menuItem.getChoice_one() );
                            } else {

                                menuCart = new MenuCart(menuItem.getUniqueId(), menuItem.getChoice_two(), selectedChoice, menuPrice, selectedQty, menuItem1);
                            }
                        } else {
                            menuCart = new MenuCart(menuItem.getUniqueId(), "", 0, menuPrice, selectedQty, menuItem1);

                        }

                        LocalDataServiceImpl.getInstance(mApp).addMenuCart(menuCart);
                        finish();
                    }else{

                        MenuCart menuCart = menu.get(0);
                        menuCart.setMenu_qty(selectedQty);
                        menuCart.setMenu_choice(selectedChoice);
                        menuCart.setMenu_price(menuPrice);
                        menuCart.save();
                        finish();

                    }
                }


                break;

            case R.id.imgbtnDelete:

                MenuCart menuCart = menu.get(0);
                menuCart.delete();
                finish();

                break;

        }

    }
}
