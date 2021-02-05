package com.ennbou.factureskafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class FactureSerializer implements Serializer<Facture> {

  @Override
  public byte[] serialize(String s, Facture facture) {
    byte[] retVal = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      retVal = objectMapper.writeValueAsString(facture).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retVal;
  }

}
