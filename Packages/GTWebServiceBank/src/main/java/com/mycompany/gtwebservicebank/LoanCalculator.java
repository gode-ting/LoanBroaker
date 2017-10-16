/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gtwebservicebank;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;

/**
 *
 * @author abj
 */
public class LoanCalculator {

    private float maxInterest = 15.0f;
    private float minInterest = 2.0f;
    private int maxCreditScore = 800;

    public LoanCalculator() {
    }

    public JSONObject getInterestRate(String ssn, int creditScore) {
        float interestRate = calculateInterestRate(creditScore);
        JSONObject response = new JSONObject();
        response.put("interestRate", interestRate);
        response.put("ssn", ssn);
        System.out.println("{LoanCalculator} - " + response.toJSONString());
        return response;
    }

    //Based on 2 grænseværdier, hvis du har en høj creditscore får den en lavere og omvendt.
    public float calculateInterestRate(int creditScore) {
        float interestRate = 0.0f;
        interestRate = maxInterest - (maxInterest * (maxCreditScore - creditScore) / maxCreditScore);
        return interestRate;
    }
}
