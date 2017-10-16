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

    public void getInterestRate(HashMap application) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try { // Call Web Service Operation
                    com.mycompany.gtwebservicebank.GoTingWSBank_Service service = new com.mycompany.gtwebservicebank.GoTingWSBank_Service();
                    com.mycompany.gtwebservicebank.GoTingWSBank port = service.getGoTingWSBankPort();
                    // TODO initialize WS operation arguments here
                    String ssn = application.get("ssn").toString();
                    System.out.println("ssn: " + ssn);
                    int creditScore = (int)application.get("creditScore");
                    System.out.println("creditScore: " + creditScore);
                    double loanAmount = (double)application.get("loanAmount");
                    System.out.println("loanAmount: " + loanAmount);
                    int loanDuration = (int)application.get("loanDuration");
                    System.out.println("loanDuration: " + loanDuration);
                    // TODO process result here
                    byte[] result = port.loanRequest(ssn, creditScore, (float) loanAmount, loanDuration);
                    System.out.println(result);
                    System.out.println(SerializationUtils.deserialize(result));
                    
                    delegate.didGetInterestRateWithOptionalException(result, null);
                } catch (Exception ex) {
                    delegate.didGetInterestRateWithOptionalException(null, ex);
                }

//                    try { // Call Web Service Operation
//                        app.rulebase.GetBankWebServices service = new app.rulebase.GetBankWebServices();
//                        app.rulebase.CreditScoreWebService port = service.getCreditScoreWebServicePort();
//                        // TODO initialize WS operation arguments here
//                        int creditScore = (int) application.get("creditScore");
//                        // TODO process result here
//                        byte[] result = port.getBankRules(creditScore);
//                        HashMap resultMap = (HashMap)SerializationUtils.deserialize(result);
//                        resultMap.put("application", application);
//                        
//                        delegate.didGetCreditScoreWithOptionalException(resultMap, null);
//                    } catch (Exception ex) {
//                        delegate.didGetCreditScoreWithOptionalException(null, ex);
//                    }
            }
        });
        t.run();
    }

//    public static void main(String[] args) throws IOException {
//        creditScore("123456-1234");
//    }
}
