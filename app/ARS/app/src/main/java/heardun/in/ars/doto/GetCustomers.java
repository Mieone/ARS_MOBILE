package heardun.in.ars.doto;

/**
 * Created by headrun on 13/10/15.
 */
public class GetCustomers {

    String priority;
    String active_flag;
    String custmoer_code;
    String customer_type;

    public GetCustomers(String priority, String active_flag, String custmoer_code, String customer_type) {
        this.priority = priority;
        this.active_flag = active_flag;
        this.custmoer_code = custmoer_code;
        this.customer_type = customer_type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public String getCustmoer_code() {
        return custmoer_code;
    }

    public void setCustmoer_code(String custmoer_code) {
        this.custmoer_code = custmoer_code;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }
}
