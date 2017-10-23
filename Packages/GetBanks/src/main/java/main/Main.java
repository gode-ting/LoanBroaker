/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Service.GetBanksService;
import Service.Producer;
import Service.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.CreditScoreServiceDelegate;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main implements ConsumerDelegate, CreditScoreServiceDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private GetBanksService service;
    private Producer producer;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getCreditScore_out", this);
        service = new GetBanksService(this);
        producer = new Producer("LoanBroker9.getBanks_out", "LoanBroker9.aggregator_in", this);

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        System.out.println("\n{GetBanks} -- didConsumeMessageWithOptionalException");
        if (ex == null) {
            System.out.println("Message: " + application.toString());
            service.getCreditScore(application);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didGetCreditScoreWithOptionalException(HashMap application, Exception ex) {
        System.out.println("\n{GetBanks} -- didGetCreditScoreWithOptionalException");
        if (ex == null) {
            System.out.println("Message: " + application.toString());
            producer.sendMessage(application);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        System.out.println("\n{GetBanks} -- didProduceMessageWithOptionalException");
        if (ex == null) {
            System.out.println("Message: success");
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    /**
     * @param args
     * @throws SQLException
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        new Main();
    }

}
