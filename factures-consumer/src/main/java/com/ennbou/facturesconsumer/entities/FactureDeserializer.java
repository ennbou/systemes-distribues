package com.ennbou.facturesconsumer.entities;

import com.ennbou.facturesconsumer.entities.Facture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class FactureDeserializer implements Deserializer<Facture> {
  @Override
  public Facture deserialize(String s, byte[] bytes) {
    ObjectMapper mapper = new ObjectMapper();
    Facture Facture = null;
    try {
      Facture = mapper.readValue(bytes, Facture.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Facture;
  }
}

