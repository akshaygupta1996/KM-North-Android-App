package connect.shopping.akshay.kmnorth.bean.request;

/**
 * Created by Akshay on 25-07-2017.
 */

public class AddAddressRequest {

    private String delivery_area;
    private String address;
    private String instructions;

    public AddAddressRequest(String address, String delivery_area, String instructions) {
        this.address = address;
        this.delivery_area = delivery_area;
        this.instructions = instructions;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
