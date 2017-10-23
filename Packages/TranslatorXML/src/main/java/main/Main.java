package main;

import Translators.XMLTranslator;
import app.Producer;
import app.QueueConsumer;
import interfaces.ConsumerDelegate;
import interfaces.MainInterface;
import interfaces.ProducerDelegate;
import interfaces.XMLTranslatorInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import org.json.simple.JSONObject;

public class Main implements ConsumerDelegate, ProducerDelegate, MainInterface {

    private QueueConsumer consumer;
    private Producer producer;
    private XMLTranslatorInterface xmlTranslator;
    private static final String EXCHANGE_NAME = "LoanBroker9.getRecipients_out";

    public Main() throws Exception {
        consumer = new QueueConsumer(EXCHANGE_NAME, "bank-lån-and-spar", this);
        producer = new Producer("cphbusiness.bankXML", this);
        xmlTranslator = new XMLTranslator();

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

    }

    @Override
    public void didConsumeMessageWithOptionalException(JSONObject application, Exception ex) {
        System.out.println("\n{TranslatorXML} -- didConsumeMessageWithOptionalException");
        if (ex == null) {
            String xml = xmlTranslator.translateXml(application);
            String replyTo = "LoanBroker9.banks_out";
            System.out.println("Message: " + application);
            producer.sendMessage(xml, replyTo);
        } else {
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
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
