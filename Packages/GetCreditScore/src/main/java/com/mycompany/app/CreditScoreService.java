/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.app;

import com.mycompany.interfaces.CreditScoreServiceDelegate;
import java.util.HashMap;

/**
 *
 * @author emilgras
 */
public class CreditScoreService implements Runnable {

    CreditScoreServiceDelegate delegate;

    public CreditScoreService(CreditScoreServiceDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {

    }

    public void getCreditScore(HashMap application) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try { // Call Web Service Operation
                    org.bank.credit.web.service.CreditScoreService_Service service = new org.bank.credit.web.service.CreditScoreService_Service();
                    org.bank.credit.web.service.CreditScoreService port = service.getCreditScoreServicePort();

//                    String result = Integer.toString(port.creditScore(ssn));
                    String ssn = (String) application.get("ssn");
                    int score = (int) port.creditScore(ssn);
                    System.out.println("{Credit Score} : " + score);
                    application.put("creditScore", score);
                    application.put("ssn", ((String)application.get("ssn")).replace("-", ""));
                    delegate.didGetCreditScoreWithOptionalException(application, null);

                } catch (Exception ex) {
                    delegate.didGetCreditScoreWithOptionalException(null, ex);
                }
            }
        });
        t.run();
    }

//    public static void main(String[] args) throws IOException {
//        creditScore("123456-1234");
//    }
}
