package app;

import connection.EndPoint;
import interfaces.ProducerDelegate;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;

public class Producer extends EndPoint {

    private ProducerDelegate delegate;

    public Producer(String endPointName, ProducerDelegate delegate) throws IOException, TimeoutException {
        super(endPointName);
        this.delegate = delegate;
    }

    public void sendMessage(final String object) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    channel.basicPublish("", endPointName, null, object.getBytes());
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
