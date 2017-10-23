package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rabbitmq.client.AMQP;
import connection.EndPoint;
import interfaces.ConsumerDelegate;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.tools.json.JSONReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QueueConsumer extends EndPoint implements Runnable, com.rabbitmq.client.Consumer {

    private ConsumerDelegate delegate;

    public QueueConsumer(String exhangeName, String keyBind, ConsumerDelegate delegate) throws IOException, TimeoutException {
        super(exhangeName, keyBind);
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(queueName, true, (com.rabbitmq.client.Consumer) this);
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
     *
     * @param consumerTag
     * @param env
     * @param props
     * @param body
     * @throws java.io.IOException
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope env,
            AMQP.BasicProperties props, byte[] body) throws IOException {

        try {
            String message = new String(body);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);
            
            delegate.didConsumeMessageWithOptionalException(json, null);
        } catch (ParseException ex) {
            delegate.didConsumeMessageWithOptionalException(null, ex);
            Logger.getLogger(QueueConsumer.class.getName()).log(Level.SEVERE, null, ex);
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
    }

}
