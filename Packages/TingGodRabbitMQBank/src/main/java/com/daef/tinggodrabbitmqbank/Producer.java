package com.daef.tinggodrabbitmqbank;

import com.rabbitmq.client.AMQP.BasicProperties;

import interfaces.ProducerDelegate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.json.simple.JSONObject;

public class Producer extends RabbitMQEndPointProducer {

    private ProducerDelegate delegate;

    public Producer(ProducerDelegate delegate) throws IOException, TimeoutException {
        this.delegate = delegate;
    }

    public void sendMessage(final JSONObject response, final String replyTo_exchange) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Map headers = new HashMap();
                headers.put("type", "json");
                headers.put("bankID", "TingGodRabbitMQBank");
                BasicProperties props = new BasicProperties.Builder()
                        .headers(headers)
                        .build();
                byte[] data = SerializationUtils.serialize(response.toJSONString());
                
                try {
                    channel.exchangeDeclare(replyTo_exchange, "fanout");
                    channel.basicPublish("", replyTo_exchange, props, SerializationUtils.serialize(response.toJSONString()));
                    delegate.didProduceMessageWithOptionalException(null);
                } catch (IOException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                    delegate.didProduceMessageWithOptionalException(ex);
                }
                
            }
        });
        t.run();
    }
}
