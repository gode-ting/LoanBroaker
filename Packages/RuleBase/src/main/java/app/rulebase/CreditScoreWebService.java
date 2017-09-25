package app.rulebase;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "CreditScoreWebService")
public class CreditScoreWebService {

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "getCreditScore")
    public String getCreditScore(@WebParam(name = "snn") int snn) {

        return "ssn";
    }
}
