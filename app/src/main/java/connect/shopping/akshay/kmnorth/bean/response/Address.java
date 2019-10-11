package connect.shopping.akshay.kmnorth.bean.response;

import java.io.Serializable;

/**
 * Created by Akshay on 08-07-2017.
 */

public class Address implements Serializable {

    private int uniqueId;
    private int user_id;
    private String address;
    private String delivery_area;
    private String instructions;


    public Address(String address, String delivery_area, int uniqueId, String instructions, int user_id) {
        this.address = address;
        this.delivery_area = delivery_area;
        this.uniqueId = uniqueId;
        this.instructions = instructions;
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelivery_area() {
        return delivery_area;
    }

    public void setDelivery_area(String delivery_area) {
        this.delivery_area = delivery_area;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
