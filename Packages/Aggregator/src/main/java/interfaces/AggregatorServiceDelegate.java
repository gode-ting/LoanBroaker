/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Frederik
 */
public interface AggregatorServiceDelegate {
    public void didAggregatorServiceWithOptionalException(HashMap message, Exception ex);
}
