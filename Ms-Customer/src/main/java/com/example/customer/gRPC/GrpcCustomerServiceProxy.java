package com.example.customer.gRPC;

import com.example.customer.entity.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.ulti.JsonUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;
import vn.com.msb.db.proto.customer.GRPCCustomerService.ResponseMessage;
import vn.com.msb.db.proto.customer.GRPCCustomerService.RequestMessage;
import vn.com.msb.db.proto.customer.CustomerServiceGrpc;

@GrpcService
public class GrpcCustomerServiceProxy extends CustomerServiceGrpc.CustomerServiceImplBase {

    @Autowired
    CustomerService customerService;

    private static final Logger log = LoggerFactory.getLogger(GrpcCustomerServiceProxy.class);

    public void onCompleted(StreamObserver<ResponseMessage> responseObserver, int code, Object body){
        ResponseMessage response = ResponseMessage.newBuilder().setResult(JsonUtil.toString(new GRPCResponse(code, body))).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomer(RequestMessage request, StreamObserver<ResponseMessage> responseObserver){
        log.info("Receive message from grpc " +  request.getContent());
        GRPCRequest grpcRequest = JsonUtil.toObject(request.getContent(), GRPCRequest.class);
        if(grpcRequest == null || grpcRequest.getCustomerId() == null){
            onCompleted(responseObserver, HttpStatus.SC_BAD_REQUEST, null);
            return;
        }
        Customer customer = customerService.getCustomer(grpcRequest.getCustomerId());
        onCompleted(responseObserver, HttpStatus.SC_OK, customer);
    }
}

