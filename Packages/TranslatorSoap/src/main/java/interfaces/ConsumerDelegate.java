/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import org.json.simple.JSONObject;

/**
 *
 * @author emilgras
 */
public interface ConsumerDelegate {
    public void didConsumeMessageWithOptionalException(JSONObject application, Exception ex);
}
