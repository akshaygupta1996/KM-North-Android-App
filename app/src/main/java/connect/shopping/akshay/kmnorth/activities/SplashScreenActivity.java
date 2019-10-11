package connect.shopping.akshay.kmnorth.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import connect.shopping.akshay.kmnorth.LocalDataServiceImpl;
import connect.shopping.akshay.kmnorth.OAuthDetailsRequestResponse;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.ScreenDimensions;
import connect.shopping.akshay.kmnorth.Util;

/**
 * Created by Akshay on 30-06-2017.
 */

public class SplashScreenActivity extends KMNorthActivity {
    private static final int SPLASH_TIME = 2000;
    public static final String TAG_VERSION_CODE = "versionCode";
    private Handler mHandler;
    private Runnable nextActivityRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initScreenDimensions();
        init();
    }

    /**
     * Starts a handler till SPLASH_TIME,later opens NextScreen
     */
    private void init() {
//        Util.checkNetworkStatus(this);
//        if (HealthCocoConstants.isNetworkOnline) {
//            checkVersion();
//        } else {
        mHandler = new Handler();
        nextActivityRunnable = new Runnable() {

            @Override
            public void run() {
                openNextActivity();
            }
        };
        mHandler.postDelayed(nextActivityRunnable, SPLASH_TIME);
//        }
    }

    private void checkVersion() {
//        String versionName = Util.getVersionName(this);
//        if (!Util.isNullOrBlank(versionName)) {
//            String[] parts = versionName.split("\\.");
//            LogUtils.LOGD(TAG, "parts size " + parts.length);
//            Util.checkNetworkStatus(this);
//            if (HealthCocoConstants.isNetworkOnline) {
//                WebDataServiceImpl.getInstance(mApp).checkVersion(Integer.class, new VersionCheckRequest(AppType.BUSINESS, DeviceType.ANDROID, parts), this, this);
//            }
//        }
    }

    private void openNextActivity() {

       FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

       OAuthDetailsRequestResponse user= LocalDataServiceImpl.getInstance(mApp).getUser();
        if (user != null) {
            Intent intent = new Intent(this, Navigation_drawer.class);
            startActivity(intent);
//            openCommonOpenUpActivity(CommonOpenUpFragmentType.HOME_FRAGMENT);
        } else {
            openLogInActivity();
            //openCommonOpenUpActivity(CommonOpenUpFragmentType.HOME_FRAGMENT);
        }
        finishAffinity();
    }

    protected void openLogInActivity() {

        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
//        openCommonOpenUpActivity(CommonOpenUpFragmentType.LOG_IN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHandler != null && nextActivityRunnable != null)
            mHandler.removeCallbacks(nextActivityRunnable);
    }

    private void initScreenDimensions() {
        Util.printScreenDensity(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenDimensions.SCREEN_WIDTH = size.x;
        ScreenDimensions.SCREEN_HEIGHT = size.y;
    }

    @Override
    public void onBackPressed() {
    }
}
