package com.example.api.controller;

import com.example.api.base.ServiceInterface;
import com.example.bean.KafkaRequest;
import com.example.bean.KafkaResponse;
import com.example.bean.MambuError;
import com.example.ulti.CommonHelper;
import com.example.ulti.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@Autowired
	CommonHelper commonHelper;

	@Autowired
	AsynExecuteClass asynExecuteClass;

	@Value("${microservice.packet}")
	private String packet;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${kafka.topic.request}")
	private String topicListener;

	@KafkaListener(topics = "${kafka.topic.request}", containerFactory = "kafkaListenerContainerFactory")
	@SendTo()
	public KafkaResponse receive(String kafkaMessageString
	) {
		log.info("message data from kafka:" + kafkaMessageString);
		KafkaRequest kafkaMessage = JsonUtil.toObject(kafkaMessageString, KafkaRequest.class);
		if (kafkaMessage == null) {
			new ResponseEntity(MambuError.INVALID_PARAMETERS, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ServiceInterface service = commonHelper.loadClassService(packet, kafkaMessage.getServiceName());
		if (service == null) {
			new ResponseEntity(MambuError.SERVICE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		if(service.isAsyn()) {
			log.info("Task asyn");
			asynExecuteClass.runTask(kafkaMessage,service);
			log.info("Return result kafka asyn");
			return new KafkaResponse(200, "SUCCESS");
		}
		ResponseEntity<Object> ressult =  service.executeString(kafkaMessage.getMessageSn(), kafkaMessage.getUserInfo(), kafkaMessage.getParamCondition(), null);
		KafkaResponse kafkaResponse = new KafkaResponse(ressult.getStatusCodeValue(), ressult.getBody());
		return kafkaResponse;
	}
}
