/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daef.tinggodrabbitmqbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ConsumerDelegate;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.json.simple.JSONObject;

/**
 *
 * @author abj
 */
public class Main implements ConsumerDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private Producer producer;
    private LoanCalculator calculator;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.TingGodRabbitMQBank", this);
        producer = new Producer(this);
        calculator = new LoanCalculator();

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(byte[] body, String replyTo, IOException ex) {
        System.out.println("didConsumeMessageWithOptionalException {Message Bank}");
        if (ex == null) {

            try {
                String json = new String(body);
                ObjectMapper mapper = new ObjectMapper();
                LoanRequest loanRequest = mapper.readValue(json, LoanRequest.class);
                
                
                JSONObject response = calculator.getInterestRate(
                        loanRequest.getSsn(), 
                        loanRequest.getCreditScore());
                
                System.out.println("response - " + response);
                System.out.println("response - " + response.toJSONString());
                
                System.out.println("calculated interestRate - " + response.get("interestRate"));
                
                
                producer.sendMessage(response, replyTo);

            } catch (UnsupportedEncodingException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        if (ex == null) {
            System.out.println("Did send message to normalizer with success :-)");
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
