package connect.shopping.akshay.kmnorth;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connect.shopping.akshay.kmnorth.activities.KMNorthApplication;
import connect.shopping.akshay.kmnorth.bean.response.LoginResponse;

/**
 * Created by Akshay on 01-07-2017.
 */
public class GsonRequest extends JsonRequest<VolleyResponseBean> {
    int SC_UNAUTHORIZED = 401;
    public static final String SPACE = " ";
    public static final String SPACE_REPLACED = "%20";

    public static final int DEFAULT_TIME_OUT = 90 * 1000;   //90 seconds
    //    private static final String OAUTH_VALUE = "Basic aGVhbHRoY29jb0AxNjoxR1dMRjlPUk1LOUI4UUZW";
    private KMNorthApplication mApp;
    private Request.Priority priority;

    public interface ErrorListener {
        /**
         * Callback method that an error has occurred with the provided error
         * code, optional user-readable message and web service type for which
         * the error occurred.
         */
        public void onErrorResponse(VolleyResponseBean volleyResponseBean, String errorMessage);

        public void onNetworkUnavailable(WebServiceType webServiceType);
    }

    private static final String TAG = GsonRequest.class.getSimpleName();

    private HashMap<String, String> headersAdded = new HashMap<String, String>();
    private Response.Listener<VolleyResponseBean> listener;
    private WebServiceType webServiceType;
    private Class<?> responseClass;
    private static Gson gson;
    private ErrorListener errorListener;

    public Class<?> getResponseClass() {
        return responseClass;
    }

    public ErrorListener getResponseErrorListener() {
        return errorListener;
    }

    public Response.Listener<VolleyResponseBean> getResponseListener() {
        return listener;
    }

    public WebServiceType getWebServiceType() {
        return webServiceType;
    }

    /**
     * Constructor  used to execute New Requests
     *
     * @param mApp           : Application context
     * @param priority       : sets the request priority
     * @param webServiceType : which service type is called
     * @param classUser      : response class
     * @param url            : API Url
     * @param requestBody    : body to be sent to server(in json format)
     * @param headers        : headers to be added
     * @param listener       : response listener
     * @param errorListener  :  errorListener
     */
    public GsonRequest(KMNorthApplication mApp, Request.Priority priority, WebServiceType webServiceType, Class<?> classUser, String url, String requestBody,
                       Map<String, String> headers, Response.Listener<VolleyResponseBean> listener, ErrorListener errorListener) {
        // pass default error listener as null
        // since deliverError is overridden
        // use errorListener to deliver the error
        super(webServiceType.getMethodType(),
                DevConfig.buildType.getServerUrl(webServiceType) + url,
                requestBody,
                listener, null);
        LogUtils.LOGD(TAG, "GsonRequest Url " + webServiceType + " " + getUrl());
        LogUtils.LOGD(TAG, "GsonRequest body " + webServiceType + " " + requestBody + "requestType " + webServiceType.getMethodType());
        this.mApp = mApp;
        this.priority = priority;
        this.gson = new GsonBuilder().create();
        this.webServiceType = webServiceType;
        headersAdded.put("Authorization", "Bearer " + mApp.getAccessToken());
        if (headers != null)
            this.headersAdded.putAll(headers);
        this.listener = listener;
        this.responseClass = classUser;
        this.errorListener = errorListener;
    }

    /**
     * Constructor  is used only for pending Requests
     *
     * @param mApp           : Application context
     * @param priority       : sets the request priority
     * @param webServiceType : which service type is called
     * @param classUser      : response class
     * @param url            : API Url
     * @param requestBody    : body to be sent to server(in json format)
     * @param listener       : response listener
     * @param errorListener  :  errorListener
     */
    public GsonRequest(KMNorthApplication mApp, Request.Priority priority, WebServiceType webServiceType, Class<?> classUser, String url, String requestBody,
                       Response.Listener<VolleyResponseBean> listener, ErrorListener errorListener) {
        // pass default error listener as null
        // since deliverError is overridden
        // use errorListener to deliver the error
        super(webServiceType.getMethodType(),
                url,
                requestBody,
                listener, null);
        LogUtils.LOGD(TAG, "GsonRequest Url " + webServiceType + " " + getUrl());
        LogUtils.LOGD(TAG, "GsonRequest body " + webServiceType + " " + requestBody + "requestType " + webServiceType.getMethodType());
        this.mApp = mApp;
        this.priority = priority;
        this.gson = new GsonBuilder().create();
        this.webServiceType = webServiceType;
        headersAdded.put("Authorization", "Bearer " + mApp.getAccessToken());
        this.listener = listener;
        this.responseClass = classUser;
        this.errorListener = errorListener;
    }

    @Override
    public Request.Priority getPriority() {
        if (priority != null)
            return priority;
        return super.getPriority();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (webServiceType.getMethodType() == Request.Method.DELETE) {
            headersAdded.put("content-type", "application/json; charset=utf-8");
            headersAdded.put("Accept", "application/json");
        }
        if (!Util.isNullOrEmptyList(headersAdded))
            return headersAdded;
        return super.getHeaders();
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        headersAdded.put("content-type", "application/json; Charset=UTF-8");
        headersAdded.put("Accept", "application/json;  Charset=UTF-8");
        return headersAdded;
    }


    @Override
    protected void deliverResponse(VolleyResponseBean response) {
        mApp.removeFromRequestsList(getTag());
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        String errorMessage = null;
        VolleyResponseBean responseErrorObject = new VolleyResponseBean();
        if (errorListener != null) {
            try {
                if (error != null && error.networkResponse != null) {
                    if (error.networkResponse.statusCode == SC_UNAUTHORIZED) {
                        errorMessage = mApp.getResources().getString(R.string.error_unauthorised);
                        LocalDataServiceImpl.getInstance(mApp).clearDatabase();
                        mApp.openLoginActivity();
                    } else if (error.networkResponse.data != null) {
                        String dataReceived = new String(error.networkResponse.data);
                        responseErrorObject = gson.fromJson(dataReceived, VolleyResponseBean.class);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // just for loggin error displaying this block
            if (Util.isNullOrBlank(errorMessage)
                    && (responseErrorObject == null || (responseErrorObject != null) && Util.isNullOrBlank(responseErrorObject.getErrMsg()))
                    && error != null) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    LogUtils.LOGE(TAG, "GsonRequest. HTTP Status Code:" + networkResponse.statusCode);
                }

                if (error instanceof TimeoutError) {
                    errorMessage = "TimeoutError";
                    LogUtils.LOGE(TAG, "GsonRequest. TimeoutError");
                } else if (error instanceof NoConnectionError) {
                    errorMessage = "NoConnectionError";
                    LogUtils.LOGE(TAG, "GsonRequest. NoConnectionError");
                } else if (error instanceof AuthFailureError) {
                    errorMessage = "AuthFailureError";
                    LogUtils.LOGE(TAG, "GsonRequest. AuthFailureError");
                } else if (error instanceof ServerError) {
                    errorMessage = "ServerError";
                    LogUtils.LOGE(TAG, "GsonRequest. ServerError");
                } else if (error instanceof NetworkError) {
                    errorMessage = "NetworkError";
                    LogUtils.LOGE(TAG, "GsonRequest. NetworkError");
                } else if (error instanceof ParseError) {
                    errorMessage = "ParseError";
                    LogUtils.LOGE(TAG, "GsonRequest. ParseError");
                }
            }
            if (responseErrorObject == null)
                responseErrorObject = new VolleyResponseBean();
            responseErrorObject.setWebServiceType(webServiceType);
            if (Util.isNullOrBlank(errorMessage))
                errorMessage = "Error";
            // deliver error as well the request type
            // to identify which type of request failed
            errorListener.onErrorResponse(responseErrorObject, errorMessage);
            if (responseErrorObject != null && !Util.isNullOrBlank(responseErrorObject.getErrMsg())) {
                errorMessage = responseErrorObject.getErrMsg();
            }
//            if (webServiceType != WebServiceType.SEND_GCM_REGISTRATION_ID && webServiceType != WebServiceType.VERSION_CONTROL_CHECK)
//                Util.showToast(mApp.getApplicationContext(), errorMessage);
        }
    }

    @Override
    protected Response<VolleyResponseBean> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            VolleyResponseBean response = new VolleyResponseBean();
            String jsonString = new String(networkResponse.data, "UTF-8");
            LogUtils.LOGD(TAG, "GsonRequest Response " + webServiceType + " " + jsonString);
            Map<String, String> responseHeaders = networkResponse.headers;
            if (!TextUtils.isEmpty(jsonString)) {
                // cast data and datalist to the responseClass
                if (jsonString.contains(LocalDatabaseUtils.ID))
                    jsonString = jsonString.replace(LocalDatabaseUtils.ID, LocalDatabaseUtils.ID_REPLACED);
                if (jsonString.contains(LocalDatabaseUtils.FROM))
                    jsonString = jsonString.replace(LocalDatabaseUtils.FROM, LocalDatabaseUtils.FROM_VALUE);
                if (jsonString.contains(LocalDatabaseUtils.TO))
                    jsonString = jsonString.replace(LocalDatabaseUtils.TO, LocalDatabaseUtils.TO_VALUE);
                response = getResponseBasedOnWebServiceType(response, jsonString);
                if (response != null && responseClass != null) {
                    if (response.getData() != null) {
                        if (response.getData() instanceof Map)
                            response.setData(getObjectFromLinkedTreeMap(responseClass, response.getData()));
                    }
                    if (!Util.isNullOrEmptyList(response.getDataList()) && response.getDataList().get(0) instanceof Map) {
                        ArrayList<Object> list = new ArrayList<Object>();
                        for (Object object : response.getDataList()) {
                            list.add(getObjectFromLinkedTreeMap(responseClass, object));
                        }
                        response.setDataList(list);
                    }
                }
            }
            response.setHeaders(responseHeaders);
            response.setWebServiceType(webServiceType);

            Response<VolleyResponseBean> result = Response.success(response,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));

            return result;
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    /**
     * converting a linked map object to the specified class object
     *
     * @param classType : response class
     * @param object    : object that is to be converted
     */
    public static Object getObjectFromLinkedTreeMap(Class<?> classType, Object object) {
        try {
            String dataJson = gson.toJson(object, Map.class);
            if (!TextUtils.isEmpty(dataJson)) {
                return gson.fromJson(dataJson, classType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private VolleyResponseBean getResponseBasedOnWebServiceType(VolleyResponseBean responseBean, String jsonString) {
        switch (webServiceType) {
            case REFRESH_TOKEN:
            case GENERATE_ACCESS_TOKEN:
                LoginResponse object = gson.fromJson(jsonString, LoginResponse.class);
                responseBean.setData(object);
                return responseBean;
            default:
                return gson.fromJson(jsonString, VolleyResponseBean.class);
        }
    }

}
