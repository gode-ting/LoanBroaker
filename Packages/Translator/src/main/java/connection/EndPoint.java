package connection;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class EndPoint {

    protected final String HOST = "datdb.cphbusiness.dk";
    protected final String USERNAME = "student";
    protected final String PASSWORD = "cph";
    protected final String TYPE = "direct";

    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public EndPoint(String endPointName, String keyBind) throws IOException, TimeoutException {
        this.endPointName = endPointName;

        //Create a connection factory
//         ConnectionFactory factory = new ConnectionFactory();
//	    
//         //hostname of your rabbitmq server
//         factory.setHost(HOST);
//         factory.setUsername(USERNAME);
//         factory.setPassword(PASSWORD);
//		
//         //getting a connection
//         connection = factory.newConnection();
//	    
//         //creating a channel
//         channel = connection.createChannel();
        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
//         channel.queueDeclare(endpointName, false, false, false, null);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(endPointName, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, endPointName, "bank-lån-and-spar");

//        channel.exchangeDeclare("LoanBroker9.getRecipients_out", "direct");
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind(queueName, "LoanBroker9.getRecipients_out", "bank-lån-and-spar");
    }

    /**
     * Close channel and connection. Not necessary as it happens implicitly any
     * way.
     *
     * @throws IOException
     */
    public void close() throws IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }
}
