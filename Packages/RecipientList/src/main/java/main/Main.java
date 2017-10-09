package main;

import app.Producer;
import app.QueueConsumer;
import app.ReciepientListService;
import interfaces.ConsumerDelegate;
import interfaces.ProducerDelegate;
import interfaces.ReciepientListServiceDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;

public class Main implements ConsumerDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private Producer producer;
    private ReciepientListService service;
    
    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getBanks_out", this);
        producer = new Producer("LoanBroker9.getRecipients_out", this);
        service = new ReciepientListService();

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        if (ex == null) {
            JSONObject applicationJson = service.DistributeLoan(application);       
            for (HashMap bank : (ArrayList<HashMap>)application.get("banks")) {  
                bank.put("exhange", "cphbusiness.JSONBank");
                producer.sendMessage(applicationJson, bank, (String)bank.get("bankId"));        
            } 
        } else {
            System.out.println("{didConsumeMessageWithOptionalException} Failed with exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        if (ex == null) {
            System.out.println("success");
        } else {
            System.out.println("{didProduceMessageWithOptionalException} Failed with exception: " + ex.getLocalizedMessage());
        }
    }
    
    
    
    public static void main(String[] args) throws Exception {
        new Main();
    }


}
