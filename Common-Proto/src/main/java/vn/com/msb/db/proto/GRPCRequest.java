package vn.com.msb.db.proto;

public class GRPCRequest {

    String param;
    String customerId;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
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

    public GRPCRequest(String param, String customerId) {
        this.param = param;
        this.customerId = customerId;
    }
}
