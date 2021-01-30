package com.ennbou.stream1;

import com.ennbou.stream1.entities.Contract;
import com.ennbou.stream1.entities.ContractSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;
import java.util.function.Consumer;

@SpringBootApplication

public class Stream1Application {

  public static void main(String[] args) {
    SpringApplication.run(Stream1Application.class, args);
  }

  @Bean
  public KafkaProducer<Integer, Contract> initKafkaProducer() {
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ProducerConfig.CLIENT_ID_CONFIG, "client-prod-1");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ContractSerializer.class.getName());
    return new KafkaProducer<>(properties);
  }

}
