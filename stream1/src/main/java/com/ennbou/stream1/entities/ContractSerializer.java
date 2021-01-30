package com.ennbou.stream1.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class ContractSerializer implements Serializer<Contract> {

  @Override
  public byte[] serialize(String s, Contract contract) {
    byte[] retVal = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      retVal = objectMapper.writeValueAsString(contract).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retVal;
  }

}
