/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interfaces;

import java.util.HashMap;

/**
 *
 * @author emilgras
 */
public interface CreditScoreServiceDelegate {
    public void didGetCreditScoreWithOptionalException(HashMap application, Exception ex);
}
