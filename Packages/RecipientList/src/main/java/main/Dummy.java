package main;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.util.concurrent.TimeoutException;
import org.apache.commons.lang.SerializationUtils;
import org.json.simple.JSONObject;

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
        
        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        json.put("ssn", "280492-xxxx");
        json.put("creditScore", "750");
        json.put("loanAmount", "1000.01");
        json.put("loanDuration", "365");
        
        String jsonMessage = gson.toJson(json);

        String severity = "bank-nordea";

        channel.basicPublish(EXCHANGE_NAME, severity, null, jsonMessage.getBytes());
        System.out.println(" [x] Sent '" + severity + "':'" + jsonMessage + "'");

        channel.close();
        connection.close();
    }
}
