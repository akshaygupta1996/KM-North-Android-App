package connect.shopping.akshay.kmnorth;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Akshay on 01-07-2017.
 */
public class User extends SugarRecord {

    @Unique
    private String uniqueId;
    private String fname;
    private String lname;
    private String email;
    private String phone_number;

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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
