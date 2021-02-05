package com.ennbou.facturesconsumer.servies;

import com.ennbou.facturesconsumer.dao.FactureRepository;
import com.ennbou.facturesconsumer.entities.Facture;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class FactureConsumer {

  private final FactureRepository factureRepository;

  public FactureConsumer(FactureRepository factureRepository) {
    this.factureRepository = factureRepository;
  }

  @KafkaListener(topics = "FACTURATION", groupId = "g1")
  public void receiver(ConsumerRecord<String, Facture> cr) throws IOException {
    Facture facture = factureRepository.save(cr.value());
    System.out.println(facture + " persisted ✔");
    File file = new File("Factures.csv");
    Boolean b = false;
    if (!file.exists()) b=!b;
    FileWriter fileWriter = new FileWriter(file,true);
    if (b) {
      final String[] columns = new String[]{"id","code", "clientName", "amount"};
      fileWriter.write(String.join(",", columns)+"\n");
    }
    fileWriter.write(facture.svc()+"\n");
    fileWriter.flush();
    fileWriter.close();

    System.out.println(facture.getCode() + " inserted into CSV file ✔");
  }

}
