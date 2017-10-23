package com.daef.tinggodrabbitmqbank;

import com.rabbitmq.client.AMQP;

import interfaces.ConsumerDelegate;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueConsumer extends RabbitMQEndPointConsumer implements Runnable, com.rabbitmq.client.Consumer {

    private ConsumerDelegate delegate;
    

    public QueueConsumer(String exhangeName, ConsumerDelegate delegate) throws IOException, TimeoutException {
        super(exhangeName);
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(queueName, true, (com.rabbitmq.client.Consumer)this);
        } catch (IOException ex) {
            // delegate.didConsumeMessageWithOptionalException(null, ex);
        }
    }

    /**
     * Called when consumer is registered.
     *
     * @param consumerTag
     */

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
        delegate.didConsumeMessageWithOptionalException(body,props.getReplyTo(), null);
    }

    public void handleCancel(String consumerTag) {
    }

    public void handleCancelOk(String consumerTag) {
    }

    public void handleRecoverOk(String consumerTag) {
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
    }

    public void accept(Object t) {
    }

}
