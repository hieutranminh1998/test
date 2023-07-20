package com.example.api.controller;

import com.example.api.base.ServiceInterface;
import com.example.bean.KafkaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsynExecuteClass {
	private static final Logger log = LoggerFactory.getLogger(AsynExecuteClass.class);
	
	@Async("threadPoolTaskExecutor")
	public void runTask(KafkaRequest kafkaMessage, ServiceInterface service) {
		log.info("Process Asyn "+service);
		ResponseEntity<Object> ressult  = service.executeString(kafkaMessage.getMessageSn(), kafkaMessage.getUserInfo(), kafkaMessage.getParamCondition(), null);
		log.info("Result Asyn -->"+ressult.getStatusCodeValue());
	} 

}
