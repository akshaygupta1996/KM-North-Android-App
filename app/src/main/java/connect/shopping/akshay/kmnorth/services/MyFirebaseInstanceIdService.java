package connect.shopping.akshay.kmnorth.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import connect.shopping.akshay.kmnorth.SharedPrefManager;

/**
 * Created by Akshay on 30-07-2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {



    public static final String TOKEN_BROADCAST = "fcmbroadcast";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("myfirebaseid", "Refreshed token: " + refreshedToken);


        storeToken(refreshedToken);

        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));


        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer (refreshedToken);
    }

    private void storeToken(String refreshedToken) {

        SharedPrefManager.getInstance(getApplicationContext()).storeToken(refreshedToken);

    }


}
