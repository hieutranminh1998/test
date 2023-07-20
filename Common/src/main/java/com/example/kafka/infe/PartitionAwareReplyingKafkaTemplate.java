package com.example.kafka.infe;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.GenericMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.Nullable;

import java.util.Random;

/**
 * Specialization of the ReplyingKafkaTemplate to manage the Return Address
 * pattern, i.e. setting the REPLY_TOPIC and REPLY_PARTITION based on the
 * configured reply topic.
 * 
 * Also complete the API to resemble the KafkaTemplate (overloading the
 * sendAndReceive() method with simplified parameters, and adding corresponding
 * overloaded methods which use a configured default topic):
 */
public class PartitionAwareReplyingKafkaTemplate<K, V, R> extends ReplyingKafkaTemplate<K, V, R>
		implements com.example.kafka.infe.PartitionAwareReplyingKafkaOperations<K, V, R> {

	public PartitionAwareReplyingKafkaTemplate(ProducerFactory<K, V> producerFactory,
                                               GenericMessageListenerContainer<K, R> replyContainer) {
		super(producerFactory, replyContainer);
	}

	private TopicPartition getFirstAssignedReplyTopicPartition() {
		java.util.Collection<TopicPartition> tp =  getAssignedReplyTopicPartitions();
		int oneOrtwo = getRandomNumberUsingInts(1,3 );
		if (tp != null && tp.iterator().hasNext()) {
			TopicPartition replyPartition = tp.iterator().next();
			if(oneOrtwo==1) {
				return replyPartition;
			}else {
				if(tp.iterator().hasNext())
					replyPartition = tp.iterator().next();
				return replyPartition;
			}
			
		} else {
			TopicPartition replyPartition = new TopicPartition(getDefaultTopic(), oneOrtwo-1);
			return replyPartition;
		}
	}

	private static byte[] intToBytesBigEndian(final int data) {
		return new byte[] { (byte) ((data >> 24) & 0xff), (byte) ((data >> 16) & 0xff), (byte) ((data >> 8) & 0xff),
				(byte) ((data >> 0) & 0xff), };
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceiveDefault(@Nullable V data) {
		return sendAndReceive(getDefaultTopic(), data);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceiveDefault(K key, @Nullable V data) {
		return sendAndReceive(getDefaultTopic(), key, data);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceiveDefault(Integer partition, K key, V value) {
		return sendAndReceive(getDefaultTopic(), partition, key, value);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceiveDefault(Integer partition, Long timestamp, K key, V value) {
		return sendAndReceive(getDefaultTopic(), partition, timestamp, key, value);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceive(String topic, @Nullable V data) {
		ProducerRecord<K, V> record = new ProducerRecord<>(topic, data);
		return doSendAndReceive(record);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceive(String topic, K key, @Nullable V data) {
		ProducerRecord<K, V> record = new ProducerRecord<>(topic, key, data);
		return doSendAndReceive(record);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceive(String topic, Integer partition, K key, V data) {
		ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, key, data);
		return doSendAndReceive(record);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceive(String topic, Integer partition, Long timestamp, K key, V data) {
		ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, timestamp, key, data);
		return doSendAndReceive(record);
	}

	@Override
	public RequestReplyFuture<K, V, R> sendAndReceive(ProducerRecord<K, V> record) {
		return doSendAndReceive(record);
	}

	protected RequestReplyFuture<K, V, R> doSendAndReceive(ProducerRecord<K, V> record) {
		TopicPartition replyPartition = getFirstAssignedReplyTopicPartition();
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyPartition.topic().getBytes()))
				.add(new RecordHeader(KafkaHeaders.REPLY_PARTITION, intToBytesBigEndian(replyPartition.partition())));
		return super.sendAndReceive(record);
	}
	private static Random random = new Random();
	public int getRandomNumberUsingInts(int min, int max) {
	    return random.ints(min, max)
	      .findFirst()
	      .getAsInt();
	}

}
