import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Consumer {

  public static void main(String[] args) {


    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "client-cons-1");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG,"g1");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ContractDeserializer.class.getName());

    KafkaConsumer<Integer, Contract> kafkaConsumer = new KafkaConsumer<>(properties);

    kafkaConsumer.subscribe(Collections.singletonList("stream2"));

    long duration = 1;

    Runnable task = () -> {
      ConsumerRecords<Integer, Contract> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(duration));
      consumerRecords.forEach(cr -> {
        System.out.println(cr.key() + " => " + cr.value().getName());
      });
    };

    long startingAfter = 0;
    long period = 1;

    Executors.
            newScheduledThreadPool(1).
            scheduleAtFixedRate(task, startingAfter, period, TimeUnit.SECONDS);

  }

}
