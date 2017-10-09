/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;

/**
 *
 * @author Frederik
 */
public class ReciepientListService {

    public ReciepientListService() {
    }

    public JSONObject DistributeLoan(HashMap applicationAndBanks) {
        HashMap application = (HashMap) applicationAndBanks.get("application");
        ArrayList<HashMap> banks = (ArrayList<HashMap>) applicationAndBanks.get("banks");

        JSONObject response = new JSONObject();
        response.put("ssn", (String) application.get("ssn"));
        response.put("creditScore", (int) application.get("creditScore"));
        response.put("loanAmount", (String) application.get("loanAmount"));
        response.put("loanDuration", (String) application.get("loanDuration"));

        return response;
    }
}
