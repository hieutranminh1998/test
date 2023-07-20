package com.example.kafka.config;


import com.example.bean.KafkaResponse;
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
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@ConditionalOnExpression(value = "${enable.kafka.consumer:true}")
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Value("${kafka.topic.request}")
	private String requestTopic;

	@Value("${kafka.timeout.request-ms}")
	private Long replyTimeout;
	
	@Value("${kafka.concurrent}")
	private Integer concurrent;
	
	
	@Value("${kafka.num-partitions}")
	private Integer numPartitions;

	@Bean
	public ConsumerFactory<String, String> requestConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 500000);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, groupId + "clientidconsumerlistener");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<String>());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(requestConsumerFactory());
		factory.setReplyTemplate(replyTemplate());
		factory.setConcurrency(concurrent);
		return factory;
	}

	@Bean
	public ProducerFactory<String, KafkaResponse> replyProducerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, groupId + "clientidprocucersend");
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, KafkaResponse> replyTemplate() {
		return new KafkaTemplate<>(replyProducerFactory());
	}

	@Bean
	public NewTopic requestTopic() {
		Map<String, String> configs = new HashMap<>();
		configs.put("retention.ms", replyTimeout.toString());
		return new NewTopic(requestTopic, numPartitions, (short) 1).configs(configs);
	}
}
