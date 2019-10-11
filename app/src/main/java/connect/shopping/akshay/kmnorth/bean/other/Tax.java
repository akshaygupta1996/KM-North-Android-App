package connect.shopping.akshay.kmnorth.bean.other;

/**
 * Created by Akshay on 24-07-2017.
 */

public class Tax {

    private int id;
    private String tax_name;
    private int tax_per;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTax_name() {
        return tax_name;
    }

    public void setTax_name(String tax_name) {
        this.tax_name = tax_name;
    }

    public int getTax_per() {
        return tax_per;
    }

    public void setTax_per(int tax_per) {
        this.tax_per = tax_per;
    }
}
