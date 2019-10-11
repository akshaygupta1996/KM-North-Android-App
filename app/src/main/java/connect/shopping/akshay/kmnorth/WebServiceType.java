package connect.shopping.akshay.kmnorth;

import com.android.volley.Request;

/**
 * Created by Akshay on 01-07-2017.
 */
public enum WebServiceType {
    FRAGMENT_INITIALISATION(0, null),

    GENERATE_ACCESS_TOKEN(Request.Method.GET, "login"),

//    POST /register
    REGISTER_USER(Request.Method.POST, "register"),


    GET_ADDRESS(Request.Method.GET, "user_address"),

    ADD_ADDRESS(Request.Method.POST, "user_address/"),

    EDIT_ADDRESS(Request.Method.PUT, "address/"),

    DELETE_ADDRESS(Request.Method.DELETE,"address/"),

    //    get /v1/otp/{mobileNumber
    GENERATE_OTP(Request.Method.GET, "sendotp/"),

    //    get /v1/otp/admin/{mobileNumber}/{otpNumber}/verify
    VERIFY_OTP(Request.Method.GET, "otp/admin/"),

    FORGET_PASSWORD(Request.Method.GET, "forgetpassword/"),
    //GET /menumaincat
    GET_MAIN_CATEGORY_LIST(Request.Method.GET, "menumaincat"),

    //GET /menumaincat/{cat_id}
    GET_SUB_CATEGORY_LIST(Request.Method.GET, "menumaincat/"),

    //GET /menu
    GET_ALL_MENU_ITEM(Request.Method.GET, "menu"),

    GET_MENU_LIST(Request.Method.GET, "category/"),

    GET_TAXES(Request.Method.GET, "tax"),

    CHANGE_PASSWORD(Request.Method.PUT, "changepassword/"),

    //GET /menuorderusers/{user_id}
    GET_ORDER_HISTORY(Request.Method.GET, "menuorderusers/"),

    //GET /userpromoedit/{user_id}
    GET_USERS_PROMO(Request.Method.GET, "userpromoedit/"),

    FOOD_ORDER(Request.Method.POST, "bookmenu"),

    CHANGE_PHONE_NUMBER(Request.Method.PUT, "changephonenumber"),

    GET_PROMO_CHECK_OUT(Request.Method.GET, "phomocodeatcheckout/"),

    REFRESH_TOKEN(Request.Method.POST, "oauth/token?");

    private int methodType;
    private String url;

    private WebServiceType(int methodType, String url) {
        this.methodType = methodType;
        this.url = url;
    }

    public int getMethodType() {
        return methodType;
    }

    public String getUrl() {
        return url;
    }
}
