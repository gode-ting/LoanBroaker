package app.rulebase;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "GetBankWebServices")
public class CreditScoreWebService {

    @WebMethod(operationName = "getBankRules")
    public String getCreditScore(@WebParam(name = "snn") String ssn) {

        return ssn;
    }
}
