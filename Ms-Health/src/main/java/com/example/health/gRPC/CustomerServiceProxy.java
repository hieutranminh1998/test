package com.example.health.gRPC;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;
import vn.com.msb.db.proto.JsonUtil;
import vn.com.msb.db.proto.customer.CustomerServiceGrpc;
import vn.com.msb.db.proto.customer.GRPCCustomerService.ResponseMessage;
import vn.com.msb.db.proto.customer.GRPCCustomerService.RequestMessage;
@Service
@Scope("prototype")
public class CustomerServiceProxy {

    @GrpcClient("customerService")
    private CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub;

    public GRPCResponse getCustomer(GRPCRequest request){
        RequestMessage requestMessage = RequestMessage.newBuilder().setContent(JsonUtil.toString(request)).build();
        ResponseMessage responseMessage = this.customerServiceBlockingStub.getCustomer(requestMessage);
        return JsonUtil.toObject(responseMessage.getResult(), GRPCResponse.class);
    }

    public GRPCResponse getListCustomer(GRPCRequest request){
        RequestMessage requestMessage = RequestMessage.newBuilder().setContent(JsonUtil.toString(request)).build();
        ResponseMessage responseMessage = this.customerServiceBlockingStub.getListCustomer(requestMessage);
        return JsonUtil.toObject(responseMessage.getResult(), GRPCResponse.class);
    }
}
