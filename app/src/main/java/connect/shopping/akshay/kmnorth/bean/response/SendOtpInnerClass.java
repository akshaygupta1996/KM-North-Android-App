package connect.shopping.akshay.kmnorth.bean.response;

import java.util.ArrayList;

/**
 * Created by Akshay on 30-08-2017.
 */

public class SendOtpInnerClass {

    private ArrayList<Integer> msg_id;
    private String error;

    public SendOtpInnerClass() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Integer> getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(ArrayList<Integer> msg_id) {
        this.msg_id = msg_id;
    }
}
