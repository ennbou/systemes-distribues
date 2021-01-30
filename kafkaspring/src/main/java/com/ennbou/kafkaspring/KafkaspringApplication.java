package com.ennbou.kafkaspring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@SpringBootApplication
@EnableKafka
public class KafkaspringApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaspringApplication.class, args);
  }

@Autowired
Logger log;

@Bean
Logger initLogger() throws IOException {
  Logger logger = Logger.getLogger(this.getClass().getName());
  logger.addHandler(new FileHandler("log.xml"));
  logger.setUseParentHandlers(false);
  return logger;
}

@KafkaListener(topics = "dotnet", groupId = "g1")
public void test(ConsumerRecord<String, String> cr){
  log.info(cr.key()+" => "+cr.value());
  System.out.println("logged ✔ ");
}

}
