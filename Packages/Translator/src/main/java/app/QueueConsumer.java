
package app;

import connection.EndPoint;
import interfaces.ConsumerDelegate;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

public class QueueConsumer extends EndPoint implements Runnable, com.rabbitmq.client.Consumer {

    
    private ConsumerDelegate delegate;
    
    public QueueConsumer(String exhangeName, String keyBind, ConsumerDelegate delegate) throws IOException, TimeoutException {
        super(exhangeName, keyBind);
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
        
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + env.getRoutingKey() + "':'" + message + "'");
        
        System.out.println("hallo1");
        HashMap application = (HashMap) SerializationUtils.deserialize(body);
        delegate.didConsumeMessageWithOptionalException(application, null);
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
