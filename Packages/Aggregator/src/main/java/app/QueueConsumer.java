/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.EndPoint;
import interfaces.ConsumerDelegate;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author emilgras
 */
public class QueueConsumer extends EndPoint implements Runnable, com.rabbitmq.client.Consumer {

    private ConsumerDelegate delegate;

    public QueueConsumer(String endPointName, ConsumerDelegate delegate) throws IOException, TimeoutException {
        super(endPointName);
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true, (com.rabbitmq.client.Consumer) this);
        } catch (IOException ex) {
            delegate.didConsumeMessageWithOptionalException(null, ex);
        }
    }

    /**
     * Called when consumer is registered.
     *
     * @param consumerTag
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
            BasicProperties props, byte[] body) throws IOException {
        try {
            System.out.println("hallo123");
            String json = new String(body, "UTF-8");
            System.out.println("this is json: " + json);
            ObjectMapper mapper = new ObjectMapper();
            HashMap message = mapper.readValue(json, new TypeReference<HashMap>() {});
            delegate.didConsumeMessageWithOptionalException(message, null);

        } catch (Exception e) {
            try {
                HashMap message = (HashMap) SerializationUtils.deserialize(body);
                delegate.didConsumeMessageWithOptionalException(message, null);
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }
    }

    public void handleCancel(String consumerTag) {
    }

    public void handleCancelOk(String consumerTag) {
    }

    public void handleRecoverOk(String consumerTag) {
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
    }

    public void accept(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
