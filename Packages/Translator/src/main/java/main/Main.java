package main;

import app.Producer;
import app.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.MainInterface;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main implements ConsumerDelegate, ProducerDelegate, MainInterface {

    private QueueConsumer consumer;
    private Producer producer;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getRecipients_out", this);
        producer = new Producer("cphbusiness.bankXML", this);

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        if (ex == null) {
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

    /**
     * @param args
     * @throws SQLException
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        new Main();
    }

    @Override
    public void didProduceXml(IOException ex, byte[] data) {
        if (ex == null) {
            System.out.println("success");
            System.out.println(data);
            producer.sendMessage(data);
        } else {
            System.out.println("Failed with exception: " + ex.getLocalizedMessage());
        }
    }
}
