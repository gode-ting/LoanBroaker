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
        if (ex == null) {
            service.Aggregate(application);
        } else {
            System.out.println("Failed with exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        if (ex == null) {
            System.out.println("success");
        } else {
            System.out.println("Failed with exception: " + ex.getLocalizedMessage());
        }
    }
    
    @Override
    public void didAggregatorServiceWithOptionalException(String message, Exception ex) {
        if (ex == null) {
            System.out.println("Message Sent");
            producer.sendMessage(message);
        } else {
            System.out.println("Failed with exception: " + ex.getLocalizedMessage());
        }
    }
    
    public static void main(String[] args) throws Exception {
        new Main();
    }


}
