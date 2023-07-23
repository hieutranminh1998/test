package com.example.health.gRPC;

import com.google.protobuf.Descriptors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;
import vn.com.msb.db.proto.JsonUtil;
import vn.com.msb.db.proto.customer.CustomerServiceGrpc;
import vn.com.msb.db.proto.customer.GRPCCustomerService.ResponseMessage;
import vn.com.msb.db.proto.customer.GRPCCustomerService.RequestMessage;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CustomerServiceProxyTest {

    CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub = Mockito.mock(CustomerServiceGrpc.CustomerServiceBlockingStub.class);

    @InjectMocks
    CustomerServiceProxy customerServiceProxy = Mockito.mock(CustomerServiceProxy.class);

    @Test
    void getCustomer() {
        GRPCRequest request = Mockito.mock(GRPCRequest.class);
        GRPCResponse grpcResponse = Mockito.mock(GRPCResponse.class);
        RequestMessage requestMessage = RequestMessage.newBuilder().setContent(JsonUtil.toString(request)).build();
        ResponseMessage responseMessage = ResponseMessage.newBuilder().setField(ResponseMessage.getDescriptor().findFieldByName("result"), JsonUtil.toString(grpcResponse)).build();

        Mockito.when(customerServiceBlockingStub.getCustomer(requestMessage)).thenReturn(responseMessage);
        assertNull(customerServiceProxy.getCustomer(request));
    }
}