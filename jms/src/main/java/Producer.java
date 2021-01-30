import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Producer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic("topic1");

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        SimpleDateFormat formatD = new SimpleDateFormat("HH:mm:ss");

        for (int i = 1; i < 10; i++) {
            producer.send(session.createTextMessage("Message " + i));
            System.out.println(i + ": send a message at " + formatD.format(new Date()));
            Thread.sleep(2000);
        }

        session.close();
        connection.close();
    }
}
