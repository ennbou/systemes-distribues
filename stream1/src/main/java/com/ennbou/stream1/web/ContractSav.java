package com.ennbou.stream1.web;


import com.ennbou.stream1.entities.Contract;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractSav {

  private final KafkaProducer<Integer, Contract> kafkaProducer;

  public ContractSav(KafkaProducer<Integer, Contract> kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }


  @PostMapping("/contracts/save")
  public String save(@RequestBody Contract contract) {
    ProducerRecord<Integer, Contract> producerRecord = new ProducerRecord<>("stream2", 1, contract);
    kafkaProducer.send(producerRecord, (rm, e) -> {
      System.out.println("sent " + contract.getId() + " with key " + 1 + " to " + rm.topic());
    });
    return "sent " + contract.getCode() + " with key " + 1 + " to " + producerRecord.topic();
  }

}
