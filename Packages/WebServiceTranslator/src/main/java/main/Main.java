/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Service.Service;
import Service.Producer;
import Service.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.CreditScoreServiceDelegate;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import org.json.simple.JSONObject;

public class Main implements ConsumerDelegate, CreditScoreServiceDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private Service service;
    private Producer producer;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getWebService_in", this);
        service = new Service(this);
        producer = new Producer("LoanBroker9.banks_out", this);

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        System.out.println("\n{GetWebService} -- didConsumeMessageWithOptionalException");
        if (ex == null) {
            System.out.println("Message: " + application.toString());
            service.getInterestRate(application);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didGetInterestRateWithOptionalException(byte[] application, Exception ex) {
        System.out.println("\n{GetWebService} -- didGetInterestRateWithOptionalException");
        if (ex == null) {
            System.out.println("Message: " + application.toString());
            producer.sendMessage(application);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        System.out.println("\n{GetWebService} -- didProduceMessageWithOptionalException");
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
