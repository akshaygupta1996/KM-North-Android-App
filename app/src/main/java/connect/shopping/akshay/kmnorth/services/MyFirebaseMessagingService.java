package connect.shopping.akshay.kmnorth.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import connect.shopping.akshay.kmnorth.MyNotificationManager;
import connect.shopping.akshay.kmnorth.activities.Navigation_drawer;

import static android.content.ContentValues.TAG;

/**
 * Created by Akshay on 31-07-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());


        notifyUser("KM North", remoteMessage.getNotification().getBody());

    }


    public void notifyUser(String from, String notification){

        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());

        myNotificationManager.showNotification(from, notification, new Intent(getApplicationContext(), Navigation_drawer.class));

    }




}
