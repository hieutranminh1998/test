package com.example.kafka.service;

import com.example.bean.ErrorResponse;
import com.example.bean.KafkaRequest;
import com.example.bean.KafkaResponse;
import com.example.bean.MambuError;
import com.example.kafka.infe.CompletableFutureReplyingKafkaOperations;
import com.example.ulti.JsonUtil;
import com.example.ulti.StringUtil;
import cores.model.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.KafkaException;


import java.util.concurrent.ExecutionException;

public class KafkaConnection {
	private static final Logger log = LoggerFactory.getLogger(KafkaConnection.class);
	protected CompletableFutureReplyingKafkaOperations<String, String, KafkaResponse> requestReplyKafkaTemplate  = (CompletableFutureReplyingKafkaOperations<String, String, KafkaResponse>) ApplicationContextHolderProxy.getContext().getBean("requestReplyKafkaTemplate");

	protected KafkaResponse sendAndReceiveHanderError(KafkaRequest requestKafka) throws ApiException {
		KafkaResponse response = sendAndReceive(requestKafka);
		if(!response.isSuccess()) {
			throw createApiExpection(response);
		}
		return response;
	}
	
	protected KafkaResponse sendAndReceive(KafkaRequest requestKafka) {
		if (requestKafka == null || StringUtil.isEmpty(requestKafka.getParamCondition())
				|| StringUtil.isEmpty(requestKafka.getServiceName()) || StringUtil.isEmpty(getSendTopic())) {
			log.error("Tham so kafka message khong day du" + this.getClass().getSimpleName()  + requestKafka.toString());
			return createResponseMessage(getSendTopic(),requestKafka.getServiceName(), MambuError.INTERNAL_ERROR_KAFKA_EXCEPTION_NULL_VALUE,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(getSendTopic().contains("Email")) {
			log.info(requestKafka.getMessageSn() + "-->Begin send kafka-->" + requestKafka.getServiceName() + " to "+getSendTopic());
		}else {
			log.info(requestKafka.getMessageSn() + "-->Begin send kafka-->" + requestKafka.getServiceName() + " to "+getSendTopic() + " content:" + requestKafka.getParamCondition());
		}
		KafkaResponse response = null;
		try {
			response = requestReplyKafkaTemplate.requestReply(getSendTopic(), JsonUtil.toString(requestKafka)).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error(requestKafka.getMessageSn()+"kafka InterruptedException--->" + getSendTopic() +"--->"+ requestKafka.getServiceName());
			response = createResponseMessage(getSendTopic(),requestKafka.getServiceName(),MambuError.INTERNAL_ERROR_KAFKA_EXCEPTION_INTERRUP,
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (KafkaException e) {
			e.printStackTrace();
			log.error(requestKafka.getMessageSn()+"kafka KafkaException--->" + getSendTopic() +"--->"+ requestKafka.getServiceName());
			response = createResponseMessage(getSendTopic(),requestKafka.getServiceName(),MambuError.INTERNAL_ERROR_KAFKA_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException e) {
			e.printStackTrace();
			//timeout
			log.error(requestKafka.getMessageSn()+"kafka ExecutionException--->" + getSendTopic() +"--->"+ requestKafka.getServiceName());
			response = createResponseMessage( getSendTopic(),requestKafka.getServiceName(),MambuError.INTERNAL_ERROR_KAFKA_EXCEPTION_EXECUTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info(requestKafka.getMessageSn() + "-->Result from kafka-->" + response.getCode());
		if(!response.isSuccess())
			log.info(requestKafka.getMessageSn() + "-->Result from kafka-->" + response.getBody());
		return response;
	}

	protected void send(KafkaRequest requestKafka) {
		log.info(requestKafka.getMessageSn() + "-->Begin send kafka-->" + requestKafka.getServiceName() + " to "+getSendTopic() + " content:" + requestKafka.getParamCondition());
		if (requestKafka == null || StringUtil.isEmpty(requestKafka.getParamCondition())
				|| StringUtil.isEmpty(requestKafka.getServiceName()) || StringUtil.isEmpty(getSendTopic())) {
			log.error("Tham so kafka message khong day du");
		}
		requestReplyKafkaTemplate.requestReply(getSendTopic(), JsonUtil.toString(requestKafka))
				.thenAccept(response -> log.info("",response.getCode())).exceptionally(ex -> {
					log.error("FAIL send Kafka, because:" + ex.getCause().getMessage());
					return null;
				});
		return;
	}

	protected String getSendTopic() {
		return "";
	}

	private KafkaResponse createResponseMessage(String topic,String serviceName,MambuError response, HttpStatus status) {
		ErrorResponse errorResponse = new ErrorResponse(response);
		errorResponse.getErrors().get(0).setTimeout(true);
		StringBuilder sourceError = new StringBuilder("KafkaTopic.");
		sourceError.append(topic);
		sourceError.append(".");
		sourceError.append(serviceName);
		errorResponse.getErrors().get(0).setErrorSource(sourceError.toString());
		return new KafkaResponse(status.value(), errorResponse);
	}
	protected ApiException createApiExpection(KafkaResponse kafaResponse) {
		ApiException apiException = new ApiException(kafaResponse.getCode(), "Error");
		apiException.setErrorResponse(JsonUtil.toObject(kafaResponse.getBody(), ErrorResponse.class));
		return apiException;
	}
}
