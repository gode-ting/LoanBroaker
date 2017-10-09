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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author abj
 */
public class Main implements ConsumerDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private Producer producer;
   private LoanCalculator loanResponse;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.banks_in", this);
        producer = new Producer("LoanBroker9.tinggodMQ", this);
        loanResponse = new LoanCalculator();
        

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(byte[] body, String type, IOException ex) {
        if (ex == null) {
            
            try {
                String json = SerializationUtils.deserialize(body).toString();
                ObjectMapper mapper = new ObjectMapper();
                
                LoanRequest loanRequest = mapper.readValue(json,LoanRequest.class);
                
                producer.sendMessage(loanRequest);
                
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
            System.out.println("Did send message to aggregator with success :-)"); }
        else {
            System.out.println("Exception: " + ex.getLocalizedMessage()); }
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
