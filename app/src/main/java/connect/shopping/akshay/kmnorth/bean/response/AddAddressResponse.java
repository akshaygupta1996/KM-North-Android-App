package connect.shopping.akshay.kmnorth.bean.response;

import java.util.List;

/**
 * Created by Akshay on 25-07-2017.
 */

public class AddAddressResponse {

    private boolean status;
    private List<Address> address;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

