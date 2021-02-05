package com.ennbou.factureskafka;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Configurable
public class Producer {

  final
  KafkaTemplate<String, Facture> kafkaTemplate;

  public static final String[] NAMES = new String[]{
          "HASSAN", "MORAD", "KHADIJA", "NAJAT", "YASSIN", "KHALID", "OTHMAN"
  };

  public Producer(KafkaTemplate<String, Facture> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Bean
  void send() {

    long startAfter = 5;
    long period = 1;
    Random random = new Random();

    ListenableFutureCallback<SendResult<String, Facture>> c = new ListenableFutureCallback<SendResult<String, Facture>>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println("ERROR!!!");
      }

      @Override
      public void onSuccess(SendResult<String, Facture> r) {
        assert r != null;
        System.out.println("send " + r.getProducerRecord().value() + "to " + r.getRecordMetadata().topic());
      }
    };

    Runnable task = () -> {
      StringBuilder code = new StringBuilder();
      for (int i = 0; i < 10; i++) {
        code.append((char) (97 + random.nextInt(26)));
      }
      String clientName = NAMES[random.nextInt(NAMES.length)];
      double amount = Math.round(random.nextDouble() * 10000.0)/100.0;
      Facture facture = new Facture(code.toString(), clientName, amount);
      kafkaTemplate.send("FACTURATION", facture).addCallback(c);
    };

    Executors.
            newScheduledThreadPool(1).
            scheduleAtFixedRate(task, startAfter, period, TimeUnit.SECONDS);
  }

}
