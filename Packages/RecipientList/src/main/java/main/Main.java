package main;

import app.Producer;
import app.QueueConsumer;
import app.ReciepientListService;
import interfaces.ConsumerDelegate;
import interfaces.ProducerDelegate;
import interfaces.ReciepientListServiceDelegate;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
        System.out.println("\n{RecipientList} -- didConsumeMessageWithOptionalException");
        try {

            if (ex == null) {
                JSONObject applicationJson = service.DistributeLoan(application);
                for (HashMap bank : (ArrayList<HashMap>) application.get("banks")) {
                    System.out.println("Message seding to bank with key bidning: " + (String) bank.get("bankId"));
                    producer.sendMessage(applicationJson, bank, (String) bank.get("bankId"));
                }
            } else {
                System.out.println("Exception: " + ex.getLocalizedMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        System.out.println("\n{RecipientList} -- didConsumeMessageWithOptionalException");
        if (ex == null) {
            System.out.println("Message: success");
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }

}
