/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import interfaces.CreditScoreServiceDelegate;
import java.util.HashMap;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author emilgras
 */
public class GetBanksService implements Runnable {

    CreditScoreServiceDelegate delegate;

    public GetBanksService(CreditScoreServiceDelegate delegate) {
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
                        app.rulebase.GetBankWebServices service = new app.rulebase.GetBankWebServices();
                        app.rulebase.CreditScoreWebService port = service.getCreditScoreWebServicePort();
                        // TODO initialize WS operation arguments here
                        int creditScore = (int) application.get("creditScore");
                        // TODO process result here
                        byte[] result = port.getBankRules(creditScore);
                        HashMap resultMap = (HashMap)SerializationUtils.deserialize(result);
                        resultMap.put("application", application);
                        
                        delegate.didGetCreditScoreWithOptionalException(resultMap, null);
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
