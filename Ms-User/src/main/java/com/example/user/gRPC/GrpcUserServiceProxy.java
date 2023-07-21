package com.example.user.gRPC;

import com.example.user.entity.Customer;
import com.example.user.ulti.JsonUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;
import vn.com.msb.db.proto.user.GRPCUserService.ResponseMessage;
import vn.com.msb.db.proto.user.GRPCUserService.RequestMessage;
import vn.com.msb.db.proto.user.UserServiceGrpc;

@GrpcService
public class GrpcUserServiceProxy extends UserServiceGrpc.UserServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(GrpcUserServiceProxy.class);

    @Value("${microservice.packet}")
    private String packet;

    public void onCompleted(StreamObserver<ResponseMessage> responseObserver, int code, Object body){
        ResponseMessage response = ResponseMessage.newBuilder().setResult(JsonUtil.toString(new GRPCResponse(code, body))).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomer(RequestMessage request, StreamObserver<ResponseMessage> responseObserver){
        log.info("Receive message from grpc " +  request.getContent());
        GRPCRequest grpcRequest = JsonUtil.toObject(request.getContent(), GRPCRequest.class);
        if(grpcRequest == null){
            onCompleted(responseObserver, HttpStatus.SC_BAD_REQUEST, null);
            return;
        }
        //todo getcustomer
        Customer customer = new Customer();
        customer.setCustomerId("abc");
        onCompleted(responseObserver, HttpStatus.SC_OK, customer);
    }
}

