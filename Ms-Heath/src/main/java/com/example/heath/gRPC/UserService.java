package com.example.heath.gRPC;

import com.example.heath.entity.Customer;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;
import vn.com.msb.db.proto.JsonUtil;
import vn.com.msb.db.proto.user.GRPCUserService;
import vn.com.msb.db.proto.user.UserServiceGrpc;
import vn.com.msb.db.proto.user.GRPCUserService.ResponseMessage;
import vn.com.msb.db.proto.user.GRPCUserService.RequestMessage;
@Service
@Scope("prototype")
public class UserService{

    @GrpcClient("userService")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public GRPCResponse getCustomer(GRPCRequest request){
        RequestMessage requestMessage = RequestMessage.newBuilder().setContent(JsonUtil.toString(request)).build();
        ResponseMessage responseMessage = this.userServiceBlockingStub.getCustomer(requestMessage);
        return JsonUtil.toObject(responseMessage.getResult(), GRPCResponse.class);
    }
}
