
package app;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import connection.EndPoint;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class Producer extends EndPoint {

    private ProducerDelegate delegate;
    private Gson gson;
    
    public Producer(String endPointName, ProducerDelegate delegate) throws IOException, TimeoutException {
        super(endPointName);
        this.delegate = delegate;
        this.gson = new Gson();
    }

    public void sendMessage(JSONObject json, HashMap header, String binding) {
        System.out.println("Message sent to translator: " + binding);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AMQP.BasicProperties props = new AMQP.BasicProperties
                            .Builder()
                            .headers(header)
                            .build();
            
                    channel.basicPublish(endPointName, binding, props, gson.toJson(json).getBytes());
                    delegate.didProduceMessageWithOptionalException(null);
                } catch (IOException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.run();
    }
}

