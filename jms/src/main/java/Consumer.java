import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Consumer {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


        Destination destination = session.createTopic("topic1");

        MessageConsumer consumer = session.createConsumer(destination);

        connection.start();

        SimpleDateFormat formatD = new SimpleDateFormat("HH:mm:ss");

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println(formatD.format(new Date()) + " -> Received : " + text);
                } else {
                    System.out.println("Received: " + message);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

}
