package connect.shopping.akshay.kmnorth.bean.response;

/**
 * Created by Akshay on 30-08-2017.
 */

public class ForgetPasswordResponse {

    private boolean status;
    private SendOtpInnerClass res;

    public SendOtpInnerClass getRes() {
        return res;
    }

    public void setRes(SendOtpInnerClass res) {
        this.res = res;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
