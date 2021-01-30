import org.apache.activemq.broker.BrokerService;

public class ActiveMQServer {
    public static void main(String[] args) {
        try {
            BrokerService broker = new BrokerService();
            broker.addConnector("tcp://0.0.0.0:61616");
            broker.start();
            System.out.println("Start ActiveMQ Server ....");
            System.out.println();
            System.out.println("Press any key to stop the broker");
            System.out.println();
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

