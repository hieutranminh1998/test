package vn.com.msb.db.proto;

public class GRPCRequest {

    Object param;
    String customerId;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public GRPCRequest() {
    }

    public GRPCRequest(Object param, String customerId) {
        this.param = param;
        this.customerId = customerId;
    }
}
