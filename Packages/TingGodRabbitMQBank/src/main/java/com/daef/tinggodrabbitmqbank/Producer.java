
package com.daef.tinggodrabbitmqbank;

import com.rabbitmq.client.AMQP.BasicProperties;

import interfaces.ProducerDelegate;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

public class Producer extends RabbitMQEndPointProducer {

    private ProducerDelegate delegate;

    public Producer(String endPointName, ProducerDelegate delegate) throws IOException, TimeoutException {
        super(endPointName);
        this.delegate = delegate;
    }

    public void sendMessage(LoanRequest object) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String type = "json";
                Map headers = new HashMap();
                headers.put("type", type);
                BasicProperties props = new BasicProperties
                        .Builder()
                        .headers(headers)
                        .build();
                
                byte[] json = SerializationUtils.serialize(object);
                try {
                    channel.basicPublish(endPointName, "", props, json);
                } catch (IOException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
                delegate.didProduceMessageWithOptionalException(null);
            }
        });
        t.run();
    }
}
