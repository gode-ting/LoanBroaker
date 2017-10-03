
package interfaces;

import java.io.IOException;
import java.util.HashMap;

public interface ConsumerDelegate {
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex);
}
