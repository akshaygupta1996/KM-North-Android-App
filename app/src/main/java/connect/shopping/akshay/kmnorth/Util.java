package connect.shopping.akshay.kmnorth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import connect.shopping.akshay.kmnorth.activities.KMNorthActivity;

/**
 * Created by Akshay on 30-06-2017.
 */
public class Util {
    private static final String TAG = Util.class.getSimpleName();
    private static final String IS_CITY_DATA_LOADED = "isCityLoaded";
    private static Toast visibleToast;

    public static boolean checkNetworkStatus(Context context) {
        try {
            KMNorthConstants.isNetworkOnline = false;

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                KMNorthConstants.isNetworkOnline = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    KMNorthConstants.isNetworkOnline = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return KMNorthConstants.isNetworkOnline;
    }

    public static int getSizeFromDimen(Activity activity, int dimenId) {
        return activity.getResources().getDimensionPixelSize(dimenId);
    }

    public static String getValidatedValue(String value) {
        if (!Util.isNullOrBlank(value))
            return value.trim();
        return "";
    }

    public static void showAlert(Context context) {
        showAlert(context, "Error During Connection.");
    }

    public static void showAlert(Context context, String msg) {
        if (isNullOrBlank(msg))
            return;
        showAlert(context, "ALERT", msg);
    }

    public static void showAlert(Context context, int msgId) {
        showAlert(context, "ALERT", context.getResources().getString(msgId));
    }

    public static void showAlertSuccess(Context context, String msg) {
        if (isNullOrBlank(msg))
            return;
        showAlert(context, "Success", msg);
    }

    public static void showToast(Context context, String message) {
        if (visibleToast != null)
            visibleToast.cancel();
        visibleToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        visibleToast.show();
    }

    public static void showToast(Context context, int messageId) {
        if (visibleToast != null)
            visibleToast.cancel();
        visibleToast = Toast.makeText(context, messageId, Toast.LENGTH_SHORT);
        visibleToast.show();
    }


    public static void showAlert(Context context, String tittle, String msg) {
        if (isNullOrBlank(msg) || isNullOrBlank(tittle))
            return;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(tittle);
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.create();
        alertBuilder.show();
    }

    public static void showAlert(Context context, int tittle, int msg) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(tittle);
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.create();
        alertBuilder.show();
    }

    public static void showAlert(Context context, int tittle, String msg) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(tittle);
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.create();
        alertBuilder.show();
    }

    public static boolean isNullOrEmptyList(List<?> list) {
        return (list == null || list.size() <= 0) ? true : false;
    }

    public static boolean isNullOrEmptyList(HashMap<?, ?> hashMap) {
        return (hashMap == null || hashMap.size() <= 0) ? true : false;
    }

    public static boolean isNullOrZeroNumber(String text) {
        return (text == null || text.trim().equalsIgnoreCase("") || text.trim().equalsIgnoreCase("null")
                || Integer.valueOf(text.trim()) == 0) ? true : false;
    }

    public static boolean isNullOrBlank(String text) {
        return (text == null || text.trim().equalsIgnoreCase("") || text.trim().equalsIgnoreCase("null")) ? true
                : false;
    }

    /**
     * prints screen density as follows :
     * // return 0.75 if it's LDPI
     * // return 1.0 if it's MDPI
     * // return 1.5 if it's HDPI
     * // return 2.0 if it's XHDPI
     * // return 3.0 if it's XXHDPI
     * // return 4.0 if it's XXXHDPI
     *
     * @param activity
     */
    public static void printScreenDensity(Activity activity) {
        try {
            float density = activity.getResources().getDisplayMetrics().density;
            LogUtils.LOGD(TAG, "Screen density " + density);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Context context, View view) {
        try {
            InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getIntValue(Object object) {
        int intValue = 0;
        if (object instanceof Double) {
            intValue = ((Double) object).intValue();
        } else if (object instanceof String) {
            intValue = Integer.parseInt((String) object);
        } else if (object instanceof Float) {
            intValue = ((Float) object).intValue();
        }
        return intValue;
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static boolean isValidMobileNo(String num) {
        String MOBILE_NUMBER = "^[7-9][0-9]{9}$";
        Pattern pattern1 = Pattern.compile(MOBILE_NUMBER);
        return pattern1.matcher(num).matches();
    }

    public static String getValidatedValueOrNull(View view) {
        String value = null;
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            value = String.valueOf(editText.getText());
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            value = String.valueOf(textView.getText());
        }
        if (!Util.isNullOrBlank(value))
            return value.trim();
        return value;
    }

    public static String getValidatedValueOrBlank(View view) {
        String value = "";
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            value = String.valueOf(editText.getText());
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            value = String.valueOf(textView.getText());
        }
        if (!Util.isNullOrBlank(value))
            return value.trim();
        return value;
    }

    public static String getValidatedValueOrNull(TextView textView) {
        String value = String.valueOf(textView.getText());
        if (!Util.isNullOrBlank(value))
            return value.trim();
        return null;
    }

    public static String getValidatedValueOrNull(EditText editText) {
        String value = String.valueOf(editText.getText()).trim();
        if (!Util.isNullOrBlank(value)) {
            return value.trim();
        }
        return null;
    }

    public static Integer getValidatedIntegerValue(TextView textView) {
        String validatedValue = getValidatedValueOrNull(textView);
        try {
            if (!Util.isNullOrBlank(validatedValue)) {
                Integer value = Integer.parseInt(validatedValue);
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getValidatedWidth(int width) {
        if (width <= 0)
            return ScreenDimensions.SCREEN_WIDTH;
        return width;
    }

    public static int getValidatedHeight(int height) {
        if (height <= 0)
            return ScreenDimensions.SCREEN_HEIGHT;
        return height;
    }

    public static String getStringFromByteArray(byte[] b) {
        if (b != null) {
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            LogUtils.LOGD(TAG, imageEncoded);
            return imageEncoded;
        }
        return null;
    }

    public static void setFocusToEditText(KMNorthActivity mActivity, View editName) {
        editName.setFocusableInTouchMode(true);
        editName.requestFocus();
        showKeyboard(mActivity, editName);
    }

    public static void showKeyboard(KMNorthActivity mActivity, View editName) {
        final InputMethodManager inputMethodManager = (InputMethodManager) mActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editName, InputMethodManager.SHOW_IMPLICIT);
    }
}
