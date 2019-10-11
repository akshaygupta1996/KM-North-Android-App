package connect.shopping.akshay.kmnorth;

import com.orm.SugarRecord;

/**
 * Created by Akshay on 30-06-2017.
 */
public class OAuthDetailsRequestResponse extends SugarRecord {
//    private String grant_type;
    private String access_token;
    private String user_id;
    private String fname;
    private String lname;
    private String email;
    private String phone_number;
    private String custom_token;
    private String alt_phone_number;
    private String refcode;
//    private String client_id;
//    private String client_secret;
//    private String refresh_token;
//    private String username;
//    private String password;

    public OAuthDetailsRequestResponse() {
    }

//    public OAuthDetailsRequestResponse(String grantType, String clientId, String clientSecret, String userName, String password) {
//        this.grant_type = grantType;
//        this.client_id = clientId;
//        this.client_secret = clientSecret;
//        this.username = userName;
//        this.password = password;
//    }


//    public OAuthDetailsRequestResponse(String username, String password) {
//        this.password = password;
//        this.username = username;
//    }


    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public String getAlt_phone_number() {
        return alt_phone_number;
    }

    public void setAlt_phone_number(String alt_phone_number) {
        this.alt_phone_number = alt_phone_number;
    }

    public String getCustom_token() {
        return custom_token;
    }

    public void setCustom_token(String custom_token) {
        this.custom_token = custom_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}

