
package app;

import com.rabbitmq.client.AMQP.BasicProperties;
import connection.EndPointProducer;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer extends EndPointProducer {

    private ProducerDelegate delegate;

    public Producer(String endPointName, ProducerDelegate delegate) throws IOException, TimeoutException {
        super(endPointName);
        this.delegate = delegate;
    }

    public void sendMessage(final String object, final String replyTo) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    String type = "xml";
                    String bankID = "bankXML";
                    Map headers = new HashMap();
                    headers.put("type", type);
                    headers.put("bankID", bankID);
                    BasicProperties props = new BasicProperties
                            .Builder()
                            .headers(headers)
                            .replyTo(replyTo)
                            .build();

                    channel.basicPublish("", endPointName, props, object.getBytes());
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
