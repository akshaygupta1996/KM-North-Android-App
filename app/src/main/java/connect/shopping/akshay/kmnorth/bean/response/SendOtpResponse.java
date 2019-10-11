package connect.shopping.akshay.kmnorth.bean.response;

/**
 * Created by Akshay on 30-08-2017.
 */

public class SendOtpResponse {

    private int otp;
    private SendOtpInnerClass res;

    public SendOtpResponse() {
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public SendOtpInnerClass getRes() {
        return res;
    }

    public void setRes(SendOtpInnerClass res) {
        this.res = res;
    }
}
