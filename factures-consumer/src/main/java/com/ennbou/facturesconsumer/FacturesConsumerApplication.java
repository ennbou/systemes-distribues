package com.ennbou.facturesconsumer;

import com.ennbou.facturesconsumer.entities.Facture;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class FacturesConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(FacturesConsumerApplication.class, args);
  }

}
