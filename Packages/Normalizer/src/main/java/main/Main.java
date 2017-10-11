/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import app.Normalizer;
import app.Producer;
import app.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emilgras
 */
public class Main implements ConsumerDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private Producer producer;
    private Normalizer normalizer;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.banks_out", this);
        producer = new Producer("LoanBroker9.aggregator_in", this);
        normalizer = new Normalizer();

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(byte[] body, Map headers, Exception ex) {
        System.out.println("didConsumeMessageWithOptionalException {Normalizer}");
        if (ex == null) {
            try {
                String response = normalizer.normalize(body, headers);
                System.out.println("{normalizer} - normalized data - " + response);
                producer.sendMessage(response);
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(Exception ex) {
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
