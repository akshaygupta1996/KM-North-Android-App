package connect.shopping.akshay.kmnorth.bean.response;

import java.util.List;

import connect.shopping.akshay.kmnorth.bean.other.Tax;

/**
 * Created by Akshay on 24-07-2017.
 */

public class TaxResponse {

    private List<Tax> taxes;

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }
}
