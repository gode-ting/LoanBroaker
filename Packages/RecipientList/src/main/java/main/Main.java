package main;

import app.Producer;
import app.QueueConsumer;
import app.ReciepientListService;
import interfaces.ConsumerDelegate;
import interfaces.ProducerDelegate;
import interfaces.ReciepientListServiceDelegate;
import java.io.IOException;
import java.util.HashMap;

public class Main implements ConsumerDelegate, ProducerDelegate, ReciepientListServiceDelegate {

    private QueueConsumer consumer;
    private Producer producer;
    private ReciepientListService service;
    
    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getBanks_out", this);
        producer = new Producer("LoanBroker9.getRecipients_out", this);
        service = new ReciepientListService(this);

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        if (ex == null) {
            service.DistributeLoan(application);
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
    public void didReciepientListServiceWithOptionalException(HashMap application, String binding, Exception ex) {
        if (ex == null) {
            producer.sendMessage(application,binding);
        } else {
            System.out.println("Failed with exception: " + ex.getLocalizedMessage());
        }
    }
    
    public static void main(String[] args) throws Exception {
        new Main();
    }


}
