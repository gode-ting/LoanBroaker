package main;

import app.AggregatorService;
import app.Producer;
import app.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.ProducerDelegate;
import interfaces.AggregatorServiceDelegate;
import java.io.IOException;
import java.util.HashMap;

public class Main implements ConsumerDelegate, ProducerDelegate, AggregatorServiceDelegate {

    private QueueConsumer consumer;
    private Producer producer;
    private AggregatorService service;
    
    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.aggregator_in", this);
        producer = new Producer("LoanBroker9.aggregator_out", this);
        service = new AggregatorService(this);
        
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        System.out.println("\n{Aggregator} -- didProduceMessageWithOptionalException");
        if (ex == null) {
            System.out.println("Message: " + application.toString());
            service.Aggregate(application);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        System.out.println("\n{Aggregator} -- didProduceMessageWithOptionalException");
        if (ex == null) {
            System.out.println("Message: success");
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }
    
    @Override
    public void didAggregatorServiceWithOptionalException(HashMap message, Exception ex) {
        System.out.println("\n{Aggregator} -- didAggregatorServiceWithOptionalException");
        if (ex == null) {
            System.out.println("Message: " + message.toString());
            producer.sendMessage(message);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }
    
    public static void main(String[] args) throws Exception {
        new Main();
    }


}
