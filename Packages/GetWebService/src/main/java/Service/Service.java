/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import interfaces.CreditScoreServiceDelegate;
import java.util.HashMap;
import org.apache.commons.lang.SerializationUtils;
import org.json.simple.JSONObject;

/**
 *
 * @author emilgras
 */
public class Service implements Runnable {

    CreditScoreServiceDelegate delegate;

    public Service(CreditScoreServiceDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {

    }

    public void getInterestRate(final HashMap application) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try { // Call Web Service Operation
                    com.mycompany.gtwebservicebank.GoTingWSBank_Service service = new com.mycompany.gtwebservicebank.GoTingWSBank_Service();
                    com.mycompany.gtwebservicebank.GoTingWSBank port = service.getGoTingWSBankPort();
                    // TODO initialize WS operation arguments here
                    String ssn = application.get("ssn").toString();
                    int creditScore = (int)application.get("creditScore");
                    double loanAmount = (double)application.get("loanAmount");
                    int loanDuration = (int)application.get("loanDuration");
                    // TODO process result here
                    byte[] result = port.loanRequest(ssn, creditScore, (float) loanAmount, loanDuration);
                    
                    delegate.didGetInterestRateWithOptionalException(result, null);
                } catch (Exception ex) {
                    delegate.didGetInterestRateWithOptionalException(null, ex);
                }
            }
        });
        t.run();
    }
}
