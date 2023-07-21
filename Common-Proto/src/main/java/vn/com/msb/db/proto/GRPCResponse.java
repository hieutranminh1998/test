package vn.com.msb.db.proto;
public class GRPCResponse {

    int code;
    String body;

    public GRPCResponse() {
    }

    public GRPCResponse(int code, Object body) {
        this.code = code;
        this.body = JsonUtil.toString(body);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
