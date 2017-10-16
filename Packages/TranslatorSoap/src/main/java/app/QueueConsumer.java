package app;

import com.rabbitmq.client.AMQP;
import interfaces.ConsumerDelegate;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import connection.EndPointConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QueueConsumer extends EndPointConsumer implements Runnable, com.rabbitmq.client.Consumer {

    private ConsumerDelegate delegate;

    public QueueConsumer(String exhangeName, String keyBind,  ConsumerDelegate delegate) throws IOException, TimeoutException {
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
            System.out.println(" [x] Received '" + env.getRoutingKey() + "':'" + message + "'");
            
            System.out.println("BEFORE JSON");
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);
            System.out.println("JSOOOOOOOON FROM TRANSLATOR - " + json);
            
            delegate.didConsumeMessageWithOptionalException(json, null);
        } catch (ParseException ex) {
            delegate.didConsumeMessageWithOptionalException(null, ex);
            Logger.getLogger(QueueConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handleCancel(String consumerTag) {
        System.out.println("444");
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        System.out.println("333");
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        System.out.println("222");
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
        System.out.println("111");
    }

    public void accept(Object t) {
        System.out.println("000");
    }

}
