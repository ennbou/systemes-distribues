import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class ContractDeserializer implements Deserializer<Contract> {

  @Override
  public Contract deserialize(String s, byte[] bytes) {
    ObjectMapper mapper = new ObjectMapper();
    Contract contract = null;
    try {
      contract = mapper.readValue(bytes, Contract.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return contract;
  }
}
