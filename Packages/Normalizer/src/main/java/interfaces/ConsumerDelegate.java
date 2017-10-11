/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.Map;

/**
 *
 * @author emilgras
 */
public interface ConsumerDelegate {
    void didConsumeMessageWithOptionalException(byte[] body, Map headers, Exception ex);
}
