
package interfaces;

import java.io.IOException;
import java.util.HashMap;

public interface ConsumerDelegate {
    public void didConsumeMessageWithOptionalException(byte[] application, String replyTo, IOException ex);
}
