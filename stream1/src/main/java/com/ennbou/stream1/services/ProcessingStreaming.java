package com.ennbou.stream1.services;

import com.ennbou.stream1.dao.ContractRepository;
import com.ennbou.stream1.entities.Contract;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ProcessingStreaming {

  private final ContractRepository contractRepository;

  public ProcessingStreaming(ContractRepository contractRepository) {
    this.contractRepository = contractRepository;
  }

  @KafkaListener(topics = "stream2", groupId = "g2")
  public void getContract(Contract contract) throws IOException {
    Contract c = contractRepository.save(contract);
    System.out.println(c.getCode() + " persisted ✔");

    File file = new File("Contracts.csv");
    Boolean b = false;
    if (!file.exists()) b=!b;
    FileWriter fileWriter = new FileWriter(file,true);
    if (b) {
      final String[] columns = new String[]{"id", "code", "clientName", "amount"};
      fileWriter.write(String.join(",", columns)+"\n");
    }
    fileWriter.write(c.toString()+"\n");
    fileWriter.flush();
    fileWriter.close();

    System.out.println(c.getCode() + " inserted into CSV file ✔");
  }



}
