package connect.shopping.akshay.kmnorth.bean.request;

/**
 * Created by Akshay on 03-07-2017.
 */

public class RegisterRequest {

    private String fname;
    private String lname;
    private String email;
    private String phone_number;
    private String password;
    private String register_ref;

    public RegisterRequest(String email, String fname, String lname, String password, String phone_number, String register_ref) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.phone_number = phone_number;
        this.register_ref = register_ref;
    }

    public String getRegister_ref() {
        return register_ref;
    }

    public void setRegister_ref(String register_ref) {
        this.register_ref = register_ref;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
