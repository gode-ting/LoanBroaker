package main;

import Translators.XMLTranslator;
import app.Producer;
import app.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.MainInterface;
import interfaces.ProducerDelegate;
import interfaces.XMLTranslatorInterface;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.commons.lang.SerializationUtils;

public class Main implements ConsumerDelegate, ProducerDelegate, MainInterface {

    private QueueConsumer consumer;
    private Producer producer;
    private XMLTranslatorInterface xmlTranslator;

    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getRecipients_out", this);
        producer = new Producer("cphbusiness.bankXML", this);
        xmlTranslator = new XMLTranslator();

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        if (ex == null) {
            OutputStream xml = xmlTranslator.translateXml(application);
            String replyTo = "LoanBroker9.banks_out";
            producer.sendMessage(SerializationUtils.serialize((Serializable) xml), replyTo);
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

}
