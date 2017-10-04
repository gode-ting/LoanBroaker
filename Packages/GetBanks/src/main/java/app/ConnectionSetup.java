package app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.HashMap;

public abstract class ConnectionSetup {

    protected Connection connection;
    protected Channel channel;
    protected String endPointName;

    public ConnectionSetup(String queueName) {
        this.endPointName = queueName;

        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("student");
            factory.setPassword("cph");

            //getting a connection
            connection = factory.newConnection();

            //creating a channel
            channel = connection.createChannel();

            //declaring a queue for this channel. If queue does not exist, it will be created on the server.
            //durability (second param) is also set as TRUE (the queue will survive a server restart)
            channel.queueDeclare(queueName, true, false, false, null);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Closes the Queue Connection. This is not needed to be called explicitly
     * as connection closure happens implicitly anyways.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.connection.close(); //closing connection, closes all the open channels
    }

}
