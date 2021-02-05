package com.ennbou.kafkastreamprocessing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class KafkaStreamProcessingApplication {

  public static final String[] NAMES = new String[]{
          "HASSAN", "MORAD", "KHADIJA", "NAJAT", "YASSIN", "KHALID", "OTHMAN"
  };

  public static void main(String[] args) {
    SpringApplication.run(KafkaStreamProcessingApplication.class, args);
  }

@Bean
public Supplier<Facture> sendFacture() {
  Random random = new Random();
  return () -> {
    StringBuilder code = new StringBuilder();
    for (int i = 0; i < 2; i++) {
      code.append((char) (97 + random.nextInt(2)));
    }
    String clientName = NAMES[random.nextInt(NAMES.length)];
    double amount = Math.round(random.nextDouble() * 10000.0)/100.0;
    Facture facture = new Facture(code.toString(), clientName, amount);
    System.out.println("send "+facture);
    return facture;
  };
}


@Bean
public Function<KStream<String,Facture>, KStream<String,Double>> factureClient() {
  return (input) -> input.
          map((k,v) -> new KeyValue<>(v.getClientName(), v.getAmount())).
          groupByKey(Grouped.with(Serdes.String(), Serdes.Double())).
          windowedBy(TimeWindows.of(Duration.ofSeconds(5))).
          reduce(Double::sum,Materialized.as("amount-of-client")).
          toStream().
          map((k,v)-> new KeyValue<>(k.key(),v));
}

@Bean
public Function<KStream<String,Facture>, KStream<String,Double>> factures5s() {
  return (input) -> input.
          map((k,v)-> new KeyValue<>(" ", v.getAmount())).
          groupByKey(Grouped.with(Serdes.String(), Serdes.Double())).
          windowedBy(TimeWindows.of(Duration.ofSeconds(5))).
          reduce(Double::sum,Materialized.as("amount-of-5-seconds")).
          toStream().
          map((k,v)-> new KeyValue<>(k.window().startTime()+" | "+k.window().endTime(),v));
}


}
