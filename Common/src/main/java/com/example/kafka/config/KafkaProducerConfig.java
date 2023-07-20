package com.example.kafka.config;

import com.example.bean.KafkaResponse;
import com.example.kafka.infe.CompletableFutureReplyingKafkaOperations;
import com.example.kafka.infe.CompletableFutureReplyingKafkaTemplate;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@ConditionalOnExpression(value = "${enable.kafka.producer:true}")
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Value("${kafka.topic.response}")
	private String replyTopic;

	@Value("${kafka.timeout.request-ms}")
	private Long replyTimeout;
	
	
	@Value("${kafka.num-partitions}")
	private Integer numPartitions;

	@Bean(name="requestReplyKafkaTemplate")
	public CompletableFutureReplyingKafkaOperations<String, String, KafkaResponse> replyKafkaTemplate() {
		CompletableFutureReplyingKafkaTemplate<String, String, KafkaResponse> requestReplyKafkaTemplate = new CompletableFutureReplyingKafkaTemplate<>(
				requestProducerFactory(), replyListenerContainer());
		Duration timeout = Duration.of(replyTimeout, ChronoUnit.MILLIS);
		requestReplyKafkaTemplate.setDefaultReplyTimeout(timeout);
		return requestReplyKafkaTemplate;
	}

	@Bean
	public ProducerFactory<String, String> requestProducerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, groupId + "clientidproducersend");
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean(name = "replyConsumerFactory")
	public ConsumerFactory<String, KafkaResponse> replyConsumerFactory() {
		JsonDeserializer<KafkaResponse> jsonDeserializer = new JsonDeserializer<>();
		jsonDeserializer.addTrustedPackages(KafkaResponse.class.getPackage().getName());
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 500000);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, groupId + "clientidconsumersend");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
	}

	@Bean
	public KafkaMessageListenerContainer<String, KafkaResponse> replyListenerContainer() {
		ContainerProperties containerProperties = new ContainerProperties(replyTopic);
		return new KafkaMessageListenerContainer<>(replyConsumerFactory(), containerProperties);
	}


	@Bean
	public NewTopic replyTopic() {
		Map<String, String> configs = new HashMap<>();
		configs.put("retention.ms", replyTimeout.toString());
		return new NewTopic(replyTopic, numPartitions, (short) 1).configs(configs);
	}

}
