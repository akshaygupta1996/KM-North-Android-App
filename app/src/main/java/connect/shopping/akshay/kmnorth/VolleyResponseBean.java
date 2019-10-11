package connect.shopping.akshay.kmnorth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Akshay on 01-07-2017.
 */
public class VolleyResponseBean {
    private WebServiceType webServiceType;

    private Map<String, String> headers;

    // success response keys
    private Object data;
    private ArrayList<Object> dataList = new ArrayList<Object>();
    private LinkedHashMap<String, Object> dataListHashMap = new LinkedHashMap<>();

    // error keys
    private String errMsg;
    protected boolean isDataFromLocal;
    protected boolean isUserOnline = true;
    protected boolean isFromLocalAfterApiSuccess;
    protected boolean breakAfterAddRequest;

    private LocalBackgroundTaskType localBackgroundTaskType;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ArrayList<Object> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Object> dataList) {
        this.dataList = dataList;
    }

    public WebServiceType getWebServiceType() {
        return webServiceType;
    }

    public void setWebServiceType(WebServiceType webServiceType) {
        this.webServiceType = webServiceType;
    }



    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isValidDataList(VolleyResponseBean response) {
        if (response != null && !Util.isNullOrEmptyList(response.getDataList()))
            return true;
        return false;
    }

    public boolean isValidData(VolleyResponseBean response) {
        if (response != null && response.getData() != null)
            return true;
        return false;
    }

    public boolean isValidateWebServiceType(VolleyResponseBean response) {
        if (response != null && response.getWebServiceType() != null)
            return true;
        return false;
    }

    public boolean isDataFromLocal() {
        return isDataFromLocal;
    }

    public void setIsDataFromLocal(boolean isDataFromLocal) {
        this.isDataFromLocal = isDataFromLocal;
    }

    public boolean isUserOnline() {
        return isUserOnline;
    }

    public void setIsUserOnline(boolean isUserOnline) {
        this.isUserOnline = isUserOnline;
    }

    public boolean isFromLocalAfterApiSuccess() {
        return isFromLocalAfterApiSuccess;
    }

    public void setIsFromLocalAfterApiSuccess(boolean isFromLocalAfterApiSuccess) {
        this.isFromLocalAfterApiSuccess = isFromLocalAfterApiSuccess;
    }

    public boolean isBreakAfterAddRequest() {
        return breakAfterAddRequest;
    }

    public void setBreakAfterAddRequest(boolean breakAfterAddRequest) {
        this.breakAfterAddRequest = breakAfterAddRequest;
    }

    public LinkedHashMap<String, Object> getDataListHashMap() {
        return dataListHashMap;
    }

    public void setDataListHashMap(LinkedHashMap<String, Object> dataListHashMap) {
        this.dataListHashMap = dataListHashMap;
    }

    public LocalBackgroundTaskType getLocalBackgroundTaskType() {
        return localBackgroundTaskType;
    }

    public void setLocalBackgroundTaskType(LocalBackgroundTaskType localBackgroundTaskType) {
        this.localBackgroundTaskType = localBackgroundTaskType;
    }
}
