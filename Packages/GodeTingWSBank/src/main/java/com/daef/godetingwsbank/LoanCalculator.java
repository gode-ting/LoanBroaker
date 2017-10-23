/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daef.godetingwsbank;

import java.util.ArrayList;
import java.util.HashMap;

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
 
    public HashMap getInterestRate (String ssn, float loanAmount, int loanDuration, int creditScore) {
       
        HashMap interestRateResults = new HashMap<String, ArrayList>();
        
        float interestRate = calculateInterestRate(ssn,loanAmount,loanDuration,creditScore);
 
        interestRateResults.put("interestRate", interestRate);
        interestRateResults.put("ssn", ssn);
        
        return interestRateResults;
    } 
    
    //Based on 2 grænseværdier, hvis du har en høj creditscore får den en lavere og omvendt.
    public float calculateInterestRate(String ssn, float loanAmount, int loanDuration, int creditScore){
        
        float interestRate = 0.0f;
       
        interestRate =  maxInterest - (maxInterest * (maxCreditScore - creditScore) / maxCreditScore);
        return interestRate;
    }
    
    public static void main(String[] args) {
        LoanCalculator loan = new LoanCalculator();
        System.out.println(loan.getInterestRate("12345678", 1000, 2, 400));
    }
}
