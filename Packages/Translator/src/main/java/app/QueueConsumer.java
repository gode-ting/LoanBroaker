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
import org.apache.commons.lang.SerializationUtils;
import org.json.simple.JSONObject;

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

        String message = new String(body);
        System.out.println(" [x] Received '" + env.getRoutingKey() + "':'" + message + "'");

        
        
        
        
        System.out.println("hallo1");
        

        JSONReader r = new JSONReader();
        System.out.println("COMOOOOM: " + r.read(message));

        Gson gson = new GsonBuilder().create();

        System.out.println("Test1 - " + gson.toJson(message));

        JsonElement messageJson = gson.toJsonTree(message);
        
        System.out.println("weeeeiiiii: " + messageJson);
        System.out.println("nexrtos: " + messageJson.getAsJsonObject());

        JSONObject applicationJson = (JSONObject) SerializationUtils.deserialize(body);
        System.out.println("Happyness: " + applicationJson);
//        delegate.didConsumeMessageWithOptionalException(application, null);
    }

    public void handleCancel(String consumerTag) {
        System.out.println("444");
    }

    public void handleCancelOk(String consumerTag) {
        System.out.println("333");
    }

    public void handleRecoverOk(String consumerTag) {
        System.out.println("222");
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
        System.out.println("111");
    }

    public void accept(Object t) {
        System.out.println("000");
    }

}
