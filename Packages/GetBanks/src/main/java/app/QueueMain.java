package com.getbanks;

import java.util.HashMap;

public class QueueMain {

    public static void main(String[] argv) throws Exception {

        final String QUEUE_NAME_CONSUMER = "LoanBroker9.getBanksConsumer";
        final String QUEUE_NAME_PRODUCER = "LoanBroker9.getBanksProducer";

        //Spawn Consumer Thread, which will always listening for the messages to be processed
        BanksConsumer consumer = new BanksConsumer(QUEUE_NAME_CONSUMER);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        //Publishes msg in the queue
        BanksProducer producer = new BanksProducer(QUEUE_NAME_PRODUCER);

        //Produce 100 msgs
        for (int i = 0; i < 100; i++) {
            HashMap message = new HashMap<>();
            message.put("My Message", i);
            producer.publishMessage(message);
            System.out.println("Message #" + i + " sent to Queue.");
        }
    }
}
