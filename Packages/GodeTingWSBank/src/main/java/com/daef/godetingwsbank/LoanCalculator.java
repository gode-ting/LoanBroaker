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

    public LoanCalculator() {
    }
    
    
    
    
    public HashMap getInterestRate (String ssn, float loanAmount, int loanDuration, int creditScore) {
        HashMap interestRateResults = new HashMap<String, ArrayList>();
        
        int interestRate = calculateInterestRate(ssn,loanAmount,loanDuration,creditScore);
       
        
        interestRateResults.put("interestRate", interestRate);
        interestRateResults.put(ssn, ssn);
        
        return interestRateResults;
    } 
    
    //WARNING THIS IS A VERY BASIC CALCULATOR
    public int calculateInterestRate(String ssn, float loanAmount, int loanDuration, int creditScore){
        
        int interestRate = 0;
       
        interestRate = (int) (loanDuration + (creditScore * 0.1) + loanAmount);
        
        
        
         //Unlucky draw
        if (ssn.endsWith("2")) 
        {
        interestRate =(int) + 0.3;
        }
        
        return interestRate;
    }
}
