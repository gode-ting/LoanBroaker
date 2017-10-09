/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daef.godetingwsbank;

import java.io.Serializable;
import java.util.HashMap;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author abj
 */
@WebService(serviceName = "GoTingWSBank")
public class GoTingWSBank {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "LoanRequest")
    public byte[] LoanRequest(@WebParam(name = "ssn") String ssn, @WebParam(name = "creditScore") int creditScore, @WebParam(name = "loanAmount") float loanAmount, @WebParam(name = "LoanDuration") int LoanDuration) {
       LoanCalculator cal = new LoanCalculator();
        
       HashMap bankResults = cal.getInterestRate(ssn, loanAmount, LoanDuration, creditScore);
       
//       Producer producer = new Producer();
//       producer.sendMessage();
       
       return SerializationUtils.serialize((Serializable) bankResults);
    }
}
