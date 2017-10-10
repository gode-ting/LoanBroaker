/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;



/**
 *
 * @author emilgras
 */
public abstract class EndPoint {

    protected final String HOST = "datdb.cphbusiness.dk";
    protected final String USERNAME = "student";
    protected final String PASSWORD = "cph";
    
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    protected String queueName;
    
    public EndPoint(String endPointName) throws IOException, TimeoutException{
         this.endPointName = endPointName;
		
         //Create a connection factory
         ConnectionFactory factory = new ConnectionFactory();
	    
         //hostname of your rabbitmq server
         factory.setHost(HOST);
         factory.setUsername(USERNAME);
         factory.setPassword(PASSWORD);
		
         //getting a connection
         connection = factory.newConnection();
	    
         //creating a channel
         channel = connection.createChannel();
	    
         //declaring a queue for this channel. If queue does not exist,
         //it will be created on the server.
         channel.queueDeclare(endPointName, false, false, false, null);
         
//         channel.exchangeDeclare(endPointName, "fanout");
//         queueName = channel.queueDeclare().getQueue();
//         channel.queueBind(queueName, endPointName, "");
    }
	
	
    /**
     * Close channel and connection. Not necessary as it happens implicitly any way. 
     * @throws IOException
     */
     public void close() throws IOException, TimeoutException{
         this.channel.close();
         this.connection.close();
     }
}
