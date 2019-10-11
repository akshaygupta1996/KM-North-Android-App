package connect.shopping.akshay.kmnorth;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akshay on 31-07-2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "fcmsharedprefdemo";
    private static final String KEY_ACCESS_TOEKN = "token";
    private static final String SHARED_PREF_ORDER_NAME = "currentorder";
    private static final String ORDER_NO = "orderno";
    private static Context ctx;

    private static SharedPrefManager mInstance;

    private SharedPrefManager(Context context){
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){

        if(mInstance==null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;

    }

    public boolean storeToken(String token){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOEKN, token);
        editor.apply();
        return true;

    }

    public String getToken(){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOEKN, null);

    }


    public boolean storeOrderNumber(int orderno){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_ORDER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ORDER_NO, orderno);
        editor.apply();
        return true;

    }

    public void clearToken(){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(KEY_ACCESS_TOEKN).commit();

    }


    public void clearOrder(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_ORDER_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(ORDER_NO).commit();
    }

    public int getOrder() {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_ORDER_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getInt(ORDER_NO, 0);
    }
}
