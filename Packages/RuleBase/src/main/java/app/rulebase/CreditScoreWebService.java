package app.rulebase;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.SerializationUtils;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "GetBankWebServices")
public class CreditScoreWebService {

    @WebMethod(operationName = "getBankRules")
    public byte[] getCreditScore(@WebParam(name = "creditScore") int creditScore) {
        AllBanks allBanks = new AllBanks();
        List<Bank> bankList = allBanks.getBanksByCreditScore(creditScore);
        return SerializationUtils.serialize((Serializable) bankList);
    }
}
