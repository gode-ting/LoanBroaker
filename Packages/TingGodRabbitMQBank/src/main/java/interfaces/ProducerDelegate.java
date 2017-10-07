
package interfaces;

import java.io.IOException;

public interface ProducerDelegate {
     public void didProduceMessageWithOptionalException(IOException ex);
}
