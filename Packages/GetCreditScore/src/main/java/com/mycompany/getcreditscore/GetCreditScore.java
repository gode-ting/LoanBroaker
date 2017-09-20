/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.getcreditscore;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author emilgras
 */
public class GetCreditScore {

    private final static String QUEUE_NAME = "LoanBroker9.hej";

    public static void main(String[] args) throws IOException {
        creditScore("123456-1234");
    }

    public static void creditScore(String ssn) throws IOException {
        try { // Call Web Service Operation
            org.bank.credit.web.service.CreditScoreService_Service service = new org.bank.credit.web.service.CreditScoreService_Service();
            org.bank.credit.web.service.CreditScoreService port = service.getCreditScoreServicePort();
            // TODO initialize WS operation arguments here
            // TODO process result here
            String result = Integer.toString(port.creditScore(ssn));
            
            System.out.println("Result = " + result);

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("student");
            factory.setPassword("cph");
            //factory.setPort(15672);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = result;

            channel.basicPublish("", QUEUE_NAME, null, result.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

    }

}
