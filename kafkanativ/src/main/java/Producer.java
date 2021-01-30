import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer {
  public static void main(String[] args) {


    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ProducerConfig.CLIENT_ID_CONFIG, "client-prod-1");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class.getName());

    KafkaProducer<String, User> kafkaProducer = new KafkaProducer<>(properties);

    AtomicInteger counter = new AtomicInteger(1);

    Random random = new Random();

    Runnable task = () -> {
      StringBuffer value = new StringBuffer();
      for (int i = 0; i < 2; i++) {value.append((char) (97 + random.nextInt(2)));}
      User user = new User(value.toString(),1);
      ProducerRecord<String, User> producerRecord = new ProducerRecord<>("stream3",  user);
      kafkaProducer.send(producerRecord, (rm, e) -> {
        System.out.println("sent " + value.toString() + " with key " + value.toString() + " to " + rm.topic());
      });
    };

    long startAfter = 0;
    long period = 1;

    Executors.
            newScheduledThreadPool(1).
            scheduleAtFixedRate(task, startAfter, period, TimeUnit.SECONDS);

  }
}
