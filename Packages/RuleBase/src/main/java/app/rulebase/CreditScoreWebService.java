package app.rulebase;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.SerializationUtils;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "GetBankWebServices")
public class CreditScoreWebService {

    @WebMethod(operationName = "getBankRules")
    public byte[] getCreditScore(@WebParam(name = "creditScore") int creditScore) {
        AllBanks service = new AllBanks();
        HashMap bankResults = service.getBanksByCreditScore(creditScore);
        return SerializationUtils.serialize((Serializable) bankResults);
    }
}
