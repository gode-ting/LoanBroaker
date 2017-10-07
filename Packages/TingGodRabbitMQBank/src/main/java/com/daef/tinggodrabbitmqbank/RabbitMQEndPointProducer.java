/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daef.tinggodrabbitmqbank;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
/**
 *
 * @author abj
 * 
 * KINDA CONFUSED? SHOULD I MAKE A NEW BANK FOR RABBITJAZZ?
 * 
 * 
 */
public class RabbitMQEndPointProducer {
    protected final String HOST = "datdb.cphbusiness.dk";
    protected final String USERNAME = "student";
    protected final String PASSWORD = "cph";
    protected final String TYPE = "direct";
    
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    
    //THIS ENDPOINTPRODUCER WILL EXCHANGE VIA DIRECT
	
    public RabbitMQEndPointProducer(String endPointName) throws IOException, TimeoutException{
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
	    
         //declaring an exchange for this channel. If exchange does not exist,
         //it will be created on the server.
         channel.exchangeDeclare(endPointName, "fanout");
         
         //SENDING INTEREST RATES FURIOUSLY
         for (int i = 0; i < 10; i++) {
             //PUBLISH SOMETHING USEFUL
			//channel.basicPublish(endPointName, "", null, message.getBytes());
			//System.out.println(" [x] Sent '" + message + "'");
		}

		channel.close();
		connection.close();
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
