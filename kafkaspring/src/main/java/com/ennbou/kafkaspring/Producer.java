package com.ennbou.kafkaspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class Producer {

  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;

  @Bean
  void send(){
    kafkaTemplate.send("dotnet","test","successfully");
  }

}
