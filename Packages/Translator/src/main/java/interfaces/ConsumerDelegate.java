
package interfaces;

import java.io.IOException;
import org.json.simple.JSONObject;

public interface ConsumerDelegate {
    public void didConsumeMessageWithOptionalException(JSONObject application, IOException ex);
}
