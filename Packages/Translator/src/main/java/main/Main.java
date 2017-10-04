package main;

import app.Producer;
import app.QueueConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import interfaces.ConsumerDelegate;
import interfaces.MainInterface;
import interfaces.ProducerDelegate;
import interfaces.XMLTranslatorInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.commons.lang.SerializationUtils;

public class Main implements ConsumerDelegate, ProducerDelegate, MainInterface {

    private QueueConsumer consumer;
    private Producer producer;
    private XMLTranslatorInterface xmlTranslator;
private static final String EXCHANGE_NAME = "LoanBroker9.getRecipients_out";
    public Main() throws Exception {
        consumer = new QueueConsumer(EXCHANGE_NAME, "bank-lån-and-spar", this);
//        producer = new Producer("cphbusiness.bankXML", this);
//        xmlTranslator = new XMLTranslator();
//
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        
    }

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        if (ex == null) {
            System.out.println("translator did consume - " + application);
            String xml = xmlTranslator.translateXml(application);
            String replyTo = "LoanBroker9.banks_out";
            producer.sendMessage(SerializationUtils.serialize(xml), replyTo);
        } else {
            System.out.println("Failed with exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        if (ex == null) {
            System.out.println("translator did send message to bank - success");
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
