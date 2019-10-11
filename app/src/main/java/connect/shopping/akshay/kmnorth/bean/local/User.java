package connect.shopping.akshay.kmnorth.bean.local;

import java.io.Serializable;

/**
 * Created by Akshay on 03-08-2017.
 */

public class User implements Serializable {

    private String fname;
    private String lname;
    private String email;
    private String phone_number;
    private String refcode;
    private String alt_phone_number;
    private int id;

    public User() {
    }

    public User(String email, String fname, int id, String lname, String phone_number, String refcode, String alt_phone_number) {
        this.email = email;
        this.fname = fname;
        this.id = id;
        this.lname = lname;
        this.phone_number = phone_number;
        this.refcode = refcode;
        this.alt_phone_number = alt_phone_number;
    }

    public String getAlt_phone_number() {
        return alt_phone_number;
    }

    public void setAlt_phone_number(String alt_phone_number) {
        this.alt_phone_number = alt_phone_number;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }
}
