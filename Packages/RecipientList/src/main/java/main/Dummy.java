package main;

import com.rabbitmq.client.*;
import java.util.HashMap;

import java.util.concurrent.TimeoutException;
import org.apache.commons.lang.SerializationUtils;

public class Dummy {

    private static final String EXCHANGE_NAME = "LoanBroker9.getRecipients_out";

    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        HashMap message = new HashMap();
        message.put("ssn", "123456-7890");
        message.put("loanAmount", "10000.0");
        message.put("loanDuration", "1973-01-01 01:00:00.0 CET");

        String severity = "bank-jyske-bank";


        channel.basicPublish(EXCHANGE_NAME, severity, null, SerializationUtils.serialize(message));
        System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

        channel.close();
        connection.close();
    }
}
