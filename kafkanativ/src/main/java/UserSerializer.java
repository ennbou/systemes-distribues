import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class UserSerializer implements Serializer<User> {

  @Override
  public byte[] serialize(String s, User user) {
    byte[] retVal = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      retVal = objectMapper.writeValueAsString(user).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retVal;
  }

}
