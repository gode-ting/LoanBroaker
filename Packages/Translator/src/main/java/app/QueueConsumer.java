package app;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import connection.EndPoint;
import interfaces.ConsumerDelegate;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

public class QueueConsumer extends EndPoint implements Runnable, Consumer {

    private ConsumerDelegate delegate;
    

    public QueueConsumer(String exhangeName, String keyBind, ConsumerDelegate delegate) throws IOException, TimeoutException {
        super(exhangeName, keyBind);
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            System.out.println("hey");
            //start consuming messages. Auto acknowledge messages.

            

//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope,
//                    AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
//            }
//        };

            channel.basicConsume(endPointName, true, this);
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
     * @param consumerTag
     * @param env
     * @param props
     * @param body
     * @throws java.io.IOException
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope env,
            AMQP.BasicProperties props, byte[] body) throws IOException {

        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + env.getRoutingKey() + "':'" + message + "'");

        System.out.println("hallo1");
        HashMap application = (HashMap) SerializationUtils.deserialize(body);
        delegate.didConsumeMessageWithOptionalException(application, null);
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
