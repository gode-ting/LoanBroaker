package app;

import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanksConsumer extends ConnectionSetup implements Runnable {

    public BanksConsumer(String queueName) {
        super(queueName);
    }

    public void run() {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true, consumer);
        } catch (IOException ex) {
            Logger.getLogger(BanksConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) { //keep listening for the message
            try {
                HashMap<String, Integer> msgMap = consumeMessage(consumer);
            } catch (IOException e) {
                System.out.println(e);
            } catch (InterruptedException ex) {
                Logger.getLogger(BanksConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Blocking method, return only when something is available from the Queue
     *
     * @return
     * @throws Exception
     */
    private HashMap<String, Integer> consumeMessage(QueueingConsumer consumer) throws IOException, InterruptedException {
        QueueingConsumer.Delivery delivery = consumer.nextDelivery(); //blocking call
        return (HashMap<String, Integer>) new HashMap<String, Integer>();
    }
}
