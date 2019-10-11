package connect.shopping.akshay.kmnorth.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import connect.shopping.akshay.kmnorth.LogUtils;
import connect.shopping.akshay.kmnorth.R;

/**
 * Created by Akshay on 30-06-2017.
 */

public class KMNorthActivity extends AppCompatActivity {
    protected static final String TAG = KMNorthActivity.class.getSimpleName();
    protected KMNorthApplication mApp;
    private static final long DEFAULT_LOADING_TIME = 20000;
    protected FragmentManager mFragmentManager;
    private Dialog alertDialog;
    private Handler mHandler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMembers();
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
    }

    private void initMembers() {
        mApp = (KMNorthApplication) getApplication();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.executePendingTransactions();
    }
//    public void openCommonOpenUpActivity(CommonOpenUpFragmentType fragmentType, String tag, Object intentData, int requestCode) {
//        Intent intent = new Intent(this, CommonOpenUpActivity.class);
//        intent.putExtra(CommonOpenUpFragmentType.TAG_FRAGMENT_TYPE_ORDINAL, fragmentType.ordinal());
//        if (!Util.isNullOrBlank(tag) && intentData != null) {
//            intent.putExtra(tag, Parcels.wrap(intentData));
//        }
//        if (requestCode == 0)
//            startActivity(intent);
//        else
//            startActivityForResult(intent, requestCode);
//    }

//    public void openCommonOpenUpActivity(CommonOpenUpFragmentType fragmentType) {
//        openCommonOpenUpActivity(fragmentType, null, null, 0);
//    }
//
//    public void openCommonOpenUpActivity(CommonOpenUpFragmentType fragmentType, int requestCode) {
//        openCommonOpenUpActivity(fragmentType, null, null, requestCode);
//    }

    public void hideLoading() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                if (mHandler != null)
                    mHandler.removeCallbacks(runnable);
                alertDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showLoading(boolean enableTouchOutside) {
        try {
            if (alertDialog == null) {
                LogUtils.LOGD(TAG, "Initialising Dialog ");
                alertDialog = new Dialog(this, R.style.Dialog_Transparent_Background);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCancelable(false);
//                alertDialog.setCanceledOnTouchOutside(true);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_loading, null);
                alertDialog.setContentView(dialogView);
            }
            View parentView = alertDialog.findViewById(R.id.parent_view);
            if (parentView != null && enableTouchOutside) {
                parentView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        hideLoading();
                        return false;
                    }
                });
            }
            LinearLayout containerLoaderMessage = (LinearLayout) alertDialog.findViewById(R.id.container_laoder_and_message);
            TextView tvMessage = (TextView) alertDialog.findViewById(R.id.tv_message);
            tvMessage.setVisibility(View.GONE);
            if (alertDialog != null && !alertDialog.isShowing()) {
                alertDialog.show();
                startHandler(containerLoaderMessage, tvMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHandler(final View containerLoaderMessage, final TextView tvMessage) {
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (tvMessage != null) {
                    containerLoaderMessage.setBackgroundColor(getResources().getColor(R.color.black_translucent));
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(R.string.loading_taking_time);
                }
            }
        };
        mHandler.postDelayed(runnable, DEFAULT_LOADING_TIME);
    }


}
