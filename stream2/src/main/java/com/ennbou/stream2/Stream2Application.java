package com.ennbou.stream2;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class Stream2Application {

  public static void main(String[] args) {
    SpringApplication.run(Stream2Application.class, args);
  }

  @Bean
  public java.util.function.Consumer<KStream<String, User>> process() {

    return input ->{

      input.map((k, user) -> KeyValue.pair("1",user.getV())).
              groupByKey(Grouped.with(Serdes.String(), Serdes.Integer())).
              windowedBy(TimeWindows.of(Duration.ofSeconds(5))).
              reduce(Integer::sum).
              toStream().foreach((k, v) -> System.out.println(k.key()+"->"+v));

      input.map((k, user) -> KeyValue.pair(user.getName(),user.getV())).
              groupByKey(Grouped.with(Serdes.String(), Serdes.Integer())).
              windowedBy(TimeWindows.of(Duration.ofSeconds(5))).
              reduce(Integer::sum).
              toStream().foreach((k, v) -> System.out.println(k.key()+"->"+v));
    };
  }
}
