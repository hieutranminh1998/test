package com.example.heath.kafka;

import com.example.bean.KafkaRequest;
import com.example.bean.KafkaResponse;
import com.example.bean.model.UserInfo;
import com.example.heath.entity.Customer;
import com.example.kafka.service.KafkaConnection;
import com.example.ulti.JsonUtil;
import com.example.ulti.UniqueKeyFactory;
import cores.model.ApiException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class KafkaUserService extends KafkaConnection {

    KafkaRequest kafkaMessage;

    @Override
    protected String getSendTopic() {
        return "request-user-topic";
    }

    public KafkaUserService() {
        super();
        init(UniqueKeyFactory.getKey(), null);
    }

    public KafkaUserService init(String messageSn, UserInfo userInfo) {
        kafkaMessage = new KafkaRequest();
        kafkaMessage.setMessageSn(messageSn);
        kafkaMessage.setUserInfo(userInfo);
        return this;
    }

    public Customer getCustomerInfo(String messageSn, String customerId) throws ApiException {
        kafkaMessage.setParamCondition(customerId);
        kafkaMessage.setServiceName("GetCustomerInfo");
        kafkaMessage.setMessageSn(messageSn);
        KafkaResponse kafaResponse = sendAndReceiveHanderError(kafkaMessage);
        return JsonUtil.toObject(kafaResponse.getBody(), Customer.class);
    }
}
