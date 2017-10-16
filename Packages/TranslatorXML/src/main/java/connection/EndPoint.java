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
    protected String queueName;

    public EndPoint(String endPointName, String keyBind) throws IOException, TimeoutException {
        this.endPointName = endPointName;
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setUsername("student");
        factory.setPassword("cph");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(endPointName, "direct");
        queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, endPointName, keyBind);
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
