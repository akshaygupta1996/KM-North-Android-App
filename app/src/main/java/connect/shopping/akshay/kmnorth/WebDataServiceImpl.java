package connect.shopping.akshay.kmnorth;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import connect.shopping.akshay.kmnorth.activities.KMNorthApplication;
import connect.shopping.akshay.kmnorth.bean.request.AddAddressRequest;
import connect.shopping.akshay.kmnorth.bean.request.MenuOrderRequest;
import connect.shopping.akshay.kmnorth.bean.request.RegisterRequest;
import connect.shopping.akshay.kmnorth.bean.response.AddAddressResponse;
import connect.shopping.akshay.kmnorth.bean.response.AddressResponse;
import connect.shopping.akshay.kmnorth.bean.response.ChangePhoneNumberResponse;
import connect.shopping.akshay.kmnorth.bean.response.DeleteAddressResponse;
import connect.shopping.akshay.kmnorth.bean.response.ForgetPasswordResponse;
import connect.shopping.akshay.kmnorth.bean.response.MainCategoryListResponse;
import connect.shopping.akshay.kmnorth.bean.response.MenuItemListResponse;
import connect.shopping.akshay.kmnorth.bean.response.MenuOrderResponse;
import connect.shopping.akshay.kmnorth.bean.response.MenuResponse;
import connect.shopping.akshay.kmnorth.bean.response.OrderHistoryResponse;
import connect.shopping.akshay.kmnorth.bean.response.RegisterResponse;
import connect.shopping.akshay.kmnorth.bean.response.SendOtpResponse;
import connect.shopping.akshay.kmnorth.bean.response.Simple;
import connect.shopping.akshay.kmnorth.bean.response.TaxResponse;
import connect.shopping.akshay.kmnorth.bean.response.UsersPromoCodeResponse;

/**
 * Created by Akshay on 01-07-2017.
 */
public class WebDataServiceImpl {
    private static WebDataServiceImpl mInstance;
    private static KMNorthApplication mApp;

    private WebDataServiceImpl() {
    }

    public static WebDataServiceImpl getInstance(KMNorthApplication application) {
        if (mInstance == null) {
            mApp = application;
            mInstance = new WebDataServiceImpl();
        }
        return mInstance;
    }


    /**
     * Common method to request for a response object
     *
     * @param serviceType      : represents which service API type is called
     * @param responseClass    : in which class the response data is supposed to cast
     * @param url              : url of service type
     * @param objBody          : body which is to be attached along with the request
     * @param headers          : extra headers that are to be included along with API call
     * @param responseListener : listener where the response is to be passed
     * @param errorListener    : listener where the error is to be fetched.
     */
    public void getResponse(WebServiceType serviceType, Class<?> responseClass, String url, Object objBody,
                            HashMap<String, String> headers, Response.Listener<VolleyResponseBean> responseListener,
                            GsonRequest.ErrorListener errorListener) {
        try {
            String body = null;
            if (objBody != null) {
                body = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED, Modifier.PUBLIC).create().toJson(objBody);
//                if (body.contains(LocalDatabaseUtils.ID_REPLACED))
//                    body = body.replace(LocalDatabaseUtils.ID_REPLACED, LocalDatabaseUtils.ID);
//                if (body.contains(LocalDatabaseUtils.FROM_VALUE))
//                    body = body.replace(LocalDatabaseUtils.FROM_VALUE, LocalDatabaseUtils.FROM);
//                if (body.contains(LocalDatabaseUtils.TO_VALUE))
//                    body = body.replace(LocalDatabaseUtils.TO_VALUE, LocalDatabaseUtils.TO);
            }
            if (url.contains(GsonRequest.SPACE))
                url = url.replace(GsonRequest.SPACE, GsonRequest.SPACE_REPLACED);
            GsonRequest request = new GsonRequest(mApp, Request.Priority.HIGH, serviceType, responseClass, url, body, headers, responseListener,
                    errorListener);
            request.setRetryPolicy(new DefaultRetryPolicy('\uea60', 1, 1.0F));
            mApp.addToRequestQueue(request, serviceType.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getResponseForLogin(WebServiceType serviceType, Class<?> responseClass, String url,
                                    HashMap<String, String> headers, Response.Listener<VolleyResponseBean> responseListener,
                                    GsonRequest.ErrorListener errorListener) {
        try {
//            String body = null;
//            if (objBody != null) {
//                body = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED, Modifier.PUBLIC).create().toJson(objBody);
////                if (body.contains(LocalDatabaseUtils.ID_REPLACED))
////                    body = body.replace(LocalDatabaseUtils.ID_REPLACED, LocalDatabaseUtils.ID);
////                if (body.contains(LocalDatabaseUtils.FROM_VALUE))
////                    body = body.replace(LocalDatabaseUtils.FROM_VALUE, LocalDatabaseUtils.FROM);
////                if (body.contains(LocalDatabaseUtils.TO_VALUE))
////                    body = body.replace(LocalDatabaseUtils.TO_VALUE, LocalDatabaseUtils.TO);
//            }
            if (url.contains(GsonRequest.SPACE))
                url = url.replace(GsonRequest.SPACE, GsonRequest.SPACE_REPLACED);
            GsonRequest request = new GsonRequest(mApp, Request.Priority.HIGH, serviceType, responseClass, url,null, headers, responseListener,
                    errorListener);
            request.setRetryPolicy(new DefaultRetryPolicy('\uea60', 1, 1.0F));
            mApp.addToRequestQueue(request, serviceType.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void checkVersion(Class<?> class1, VersionCheckRequest versionCheckRequest, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {
//        WebServiceType webServiceType = WebServiceType.VERSION_CONTROL_CHECK;
//        Util.checkNetworkStatus(mApp);
//        if (HealthCocoConstants.isNetworkOnline) {
//            String url = webServiceType.getUrl();
//            getResponse(webServiceType, class1, url, versionCheckRequest, null, responseListener,
//                    errorListener);
//        } else {
//            showUserOffline(webServiceType, responseListener);
//        }
//    }

    private void showUserOffline(WebServiceType webServiceType, Response.Listener<VolleyResponseBean> responseListener) {
        VolleyResponseBean volleyResponseBean = new VolleyResponseBean();
        volleyResponseBean.setWebServiceType(webServiceType);
        volleyResponseBean.setIsUserOnline(false);
        volleyResponseBean.setIsDataFromLocal(true);
        responseListener.onResponse(volleyResponseBean);
        Util.showToast(mApp.getApplicationContext(), R.string.user_offline);
    }

    public void generateToken(Class<?> class1, String emailPhone, String password,int flag,String fcmtoken, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {
        WebServiceType webServiceType = WebServiceType.GENERATE_ACCESS_TOKEN;
        Util.checkNetworkStatus(mApp);
        if (KMNorthConstants.isNetworkOnline) {

//            String username = oAuthDetailsRequestResponse.getUsername();
//            String password = oAuthDetailsRequestResponse.getPassword();
            String url = webServiceType.getUrl()+
                    "/"+flag+"/"+emailPhone+"/"+password+"/"+fcmtoken;

//            String json = "{\n" +
//                    "\t\"username\": \""+ username+"\",\n" +
//                    "\t\"password\": \""+ password+"\"\n" +
//                    "}";
            Log.d("URL REQUEST",url);
            getResponseForLogin(webServiceType, class1, url,null, responseListener,
                    errorListener);
        } else {
            showUserOffline(webServiceType, responseListener);
        }
    }

    public void registerUser(WebServiceType registerUser, Class<RegisterResponse> registerResponseClass, RegisterRequest
            registerRequest,Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {

        Util.checkNetworkStatus(mApp);
        if (KMNorthConstants.isNetworkOnline) {
            String url = registerUser.getUrl();
            getResponse(registerUser, registerResponseClass, url, registerRequest, null, responseListener,
                    errorListener);
        } else {
            showUserOffline(registerUser, responseListener);
        }


    }

    public void getMainMenuCategoryList(WebServiceType getMainCategoryList, Class<MainCategoryListResponse> mainCategoryListResponseClass, Object o, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener ) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = getMainCategoryList.getUrl();

            Log.d("URL REQUEST",url);
            getResponse(getMainCategoryList, mainCategoryListResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(getMainCategoryList, responseListener);
        }


    }

    public void getAddress(WebServiceType getAddress, Class<AddressResponse> addressResponseClass, String user_id ,Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {


        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){
            String url = getAddress.getUrl()+"/"+user_id;
            getResponse(getAddress, addressResponseClass, url, null, null, responseListener,
                    errorListener);
        } else {
            showUserOffline(getAddress, responseListener);
        }

    }

    public void getMenuList(WebServiceType getAllMenuItem, Class<MenuResponse> menuResponseClass, Object o,Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {


        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){
            String url = getAllMenuItem.getUrl();
            getResponse(getAllMenuItem, menuResponseClass, url, null, null, responseListener,
                    errorListener);
        } else {
            showUserOffline(getAllMenuItem, responseListener);
        }

    }




    public void getMenuItemList(WebServiceType getMenuList, Class<MenuItemListResponse> menuItemListResponseClass, int category_id, Object o, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener ) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = getMenuList.getUrl()+ category_id;

            Log.d("URL REQUEST",url);
            getResponse(getMenuList, menuItemListResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(getMenuList, responseListener);
        }

    }

    public void getTaxes(WebServiceType getTaxes, Class<TaxResponse> taxResponseClass, Object o, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener ) {
        {

            Util.checkNetworkStatus(mApp);
            if(KMNorthConstants.isNetworkOnline){

                String url = getTaxes.getUrl();

                Log.d("URL REQUEST",url);
                getResponse(getTaxes, taxResponseClass,url, null, null,responseListener, errorListener );
            }else{
                showUserOffline(getTaxes, responseListener);
            }

        }
    }

    public void getUsersUnusedPromo(WebServiceType getUsersPromo, Class<UsersPromoCodeResponse> usersPromoCodeResponseClass, String user_id, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = getUsersPromo.getUrl() + user_id;

            Log.d("URL REQUEST",url);
            getResponse(getUsersPromo, usersPromoCodeResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(getUsersPromo, responseListener);
        }
    }

    public void addUserAddress(WebServiceType addAddress, Class<AddAddressResponse> addAddressResponseClass, AddAddressRequest addAddressRequest, String user_id, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = addAddress.getUrl() + user_id;

            Log.d("URL REQUEST",url);
            getResponse(addAddress ,addAddressResponseClass,url, addAddressRequest, null,responseListener, errorListener );
        }else{
            showUserOffline(addAddress, responseListener);
        }
    }

    public void deleteAddress(WebServiceType getAddress, Class<DeleteAddressResponse> deleteAddressResponseClass, int id, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {


        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url =getAddress.getUrl() + id;

            Log.d("URL REQUEST",url);
            getResponse(getAddress ,deleteAddressResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(getAddress, responseListener);
        }

    }

    public void editAddress(WebServiceType addAddress, Class<AddAddressResponse> addAddressResponseClass, AddAddressRequest addAddressRequest, int user_id, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = addAddress.getUrl() + user_id;

            Log.d("URL REQUEST",url);
            getResponse(addAddress ,addAddressResponseClass,url, addAddressRequest, null,responseListener, errorListener );
        }else{
            showUserOffline(addAddress, responseListener);
        }
    }

    public void bookmenu(WebServiceType foodOrder, Class<MenuOrderResponse> menuOrderResponseClass, MenuOrderRequest menuOrderRequest, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {


        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = foodOrder.getUrl();

            Log.d("URL REQUEST",url);
            getResponse(foodOrder ,menuOrderResponseClass,url, menuOrderRequest, null,responseListener, errorListener );
        }else{
            showUserOffline(foodOrder, responseListener);
        }



    }

    public void getOrderHistory(WebServiceType getOrderHistory, Class<OrderHistoryResponse> orderHistoryResponseClass, String user_id,  Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = getOrderHistory.getUrl() + user_id;

            Log.d("URL REQUEST",url);
            getResponse(getOrderHistory ,orderHistoryResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(getOrderHistory, responseListener);
        }
    }

    public void changePhoneNumber(WebServiceType changePhoneNumber, Class<ChangePhoneNumberResponse> changePhoneNumberResponseClass, String phone_number, int i, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {




        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = changePhoneNumber.getUrl() + "/"+i+"/"+phone_number;

            Log.d("URL REQUEST",url);
            getResponse(changePhoneNumber ,changePhoneNumberResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(changePhoneNumber, responseListener);
        }

    }

    public void sendotp(WebServiceType generateOtp, Class<SendOtpResponse> sendOtpResponseClass, String phone_number,Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {




        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = generateOtp.getUrl()+phone_number;

            Log.d("URL REQUEST",url);
            getResponse(generateOtp ,sendOtpResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(generateOtp, responseListener);
        }

    }

    public void forgetPassword(WebServiceType forgetPassword, Class<ForgetPasswordResponse> forgetPasswordResponseClass, String phone_number, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {


        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = forgetPassword.getUrl()+phone_number;

            Log.d("URL REQUEST",url);
            getResponse(forgetPassword ,forgetPasswordResponseClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(forgetPassword, responseListener);
        }

    }

    public void changePassword(WebServiceType changePassword, Class<Simple> simpleClass, String user_id, String oldpass, String newPass, Response.Listener<VolleyResponseBean> responseListener, GsonRequest.ErrorListener errorListener) {

        Util.checkNetworkStatus(mApp);
        if(KMNorthConstants.isNetworkOnline){

            String url = changePassword.getUrl()+user_id+"/"+oldpass+"/"+newPass;

            Log.d("URL REQUEST",url);
            getResponse(changePassword ,simpleClass,url, null, null,responseListener, errorListener );
        }else{
            showUserOffline(changePassword, responseListener);
        }

    }
}
