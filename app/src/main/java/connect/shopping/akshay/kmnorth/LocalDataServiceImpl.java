package connect.shopping.akshay.kmnorth;

import android.database.sqlite.SQLiteException;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.activities.KMNorthApplication;
import connect.shopping.akshay.kmnorth.bean.local.MenuCart;
import connect.shopping.akshay.kmnorth.bean.other.MenuItem;

import static com.orm.SugarRecord.first;

/**
 * Created by Akshay on 30-06-2017.
 */
public class LocalDataServiceImpl  {
    private static final String TAG = LocalDataServiceImpl.class.getSimpleName();
    private static LocalDataServiceImpl mInstance;
    private static KMNorthApplication mApp;
    private static Gson gson;

    private LocalDataServiceImpl() {
    }


    public static LocalDataServiceImpl getInstance(KMNorthApplication application) {
        if (mInstance == null) {
            mInstance = new LocalDataServiceImpl();
            mApp = application;
            gson = new Gson();
        }
        Util.checkNetworkStatus(mApp.getApplicationContext());
        return mInstance;
    }

    private String getJsonFromObject(Object object) {
        if (object != null)
            return gson.toJson(object);
        return null;
    }

    private Object getObjectFromJson(Class<?> class1, String jsonString) {
        if (!Util.isNullOrBlank(jsonString))
            return gson.fromJson(jsonString, class1);
        return null;
    }

    //*********************************************************
    //Common Methods till next "**********************************"
    private ArrayList<Object> getObjectsListFromMap(List<?> list) {
        if (list != null) {
            ArrayList<Object> listNew = new ArrayList<Object>();
            listNew.addAll(list);
            return listNew;
        }
        return null;
    }

    private void showErrorLocal(VolleyResponseBean volleyResponseBean, GsonRequest.ErrorListener errorListener) {
        if (errorListener != null)
            errorListener.onErrorResponse(volleyResponseBean, mApp.getResources().getString(R.string
                    .error_local_data));
        else
            volleyResponseBean.setErrMsg(mApp.getResources().getString(R.string
                    .error_local_data));
    }

    private List<?> getObjectsList(Class<?> class1, String key, String value) {
        return Select.from(class1).where(com.orm.query.Condition.prop(key).eq(value)).list();
    }

    private Object getObject(Class<?> class1, String key, String value) {
        return Select.from(class1).where(com.orm.query.Condition.prop(key).eq(value)).first();
    }

    private void deleteAllFrom(Class<?> class1, String key, String value) {
        SugarRecord.deleteAll(class1, key + "= ?", value);
    }

    public OAuthDetailsRequestResponse getOAuthRequestResponse() {
        return first(OAuthDetailsRequestResponse.class);
    }

    public void addOAuthToken(OAuthDetailsRequestResponse oAuthDetailsRequestResponse) {
        OAuthDetailsRequestResponse.deleteAll(OAuthDetailsRequestResponse.class);
        oAuthDetailsRequestResponse.save();
    }

    public void addUser(User user) {
        user.save();
    }

    public void addMenuCart(MenuCart menuCart){
        menuCart.save();
    }

    public void addMenuItem(MenuItem menuItem){menuItem.save();}

    public OAuthDetailsRequestResponse getUser() {
//        return User.first(User.class);

        OAuthDetailsRequestResponse auth;
        try {
            auth = OAuthDetailsRequestResponse.first(OAuthDetailsRequestResponse.class);
        }catch(SQLiteException e){
            auth = null;
        }

        return auth;
    }

    public MenuCart getMenuCart(){
        MenuCart menuCart = MenuCart.first(MenuCart.class);
        return menuCart;
    }

    public void clearDatabase() {
        OAuthDetailsRequestResponse.deleteAll(OAuthDetailsRequestResponse.class);
    }

}