package com.daef.tinggodrabbitmqbank;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import interfaces.ConsumerDelegate;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

public class QueueConsumer extends RabbitMQEndPointProducer implements Runnable, com.rabbitmq.client.Consumer {

    private ConsumerDelegate delegate;
    

    public QueueConsumer(String exhangeName, ConsumerDelegate delegate) throws IOException, TimeoutException {
        super(exhangeName);
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(endPointName, true, (com.rabbitmq.client.Consumer)this);
        } catch (IOException ex) {
           // delegate.didConsumeMessageWithOptionalException(null, ex);
        }
    }

    /**
     * Called when consumer is registered.
     *
     * @param consumerTag
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     * @param consumerTag
     * @param env
     * @param props
     * @param body
     * @throws java.io.IOException
     */
    public void handleDelivery(String consumerTag, Envelope env,
            AMQP.BasicProperties props, byte[] body) throws IOException {

        String type = props.getHeaders().get("type").toString();

        delegate.didConsumeMessageWithOptionalException(body,type, null);
    }

    public void handleCancel(String consumerTag) {
        System.out.println("444");
    }

    public void handleCancelOk(String consumerTag) {
        System.out.println("333");
    }

    public void handleRecoverOk(String consumerTag) {
        System.out.println("222");
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
        System.out.println("111");
    }

    public void accept(Object t) {
        System.out.println("000");
    }

}
