/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;



import com.mycompany.app.CreditScoreService;
import com.mycompany.app.Producer;
import com.mycompany.app.QueueConsumer;
import com.mycompany.interfaces.ConsumerDelegate;
import com.mycompany.interfaces.CreditScoreServiceDelegate;
import com.mycompany.interfaces.ProducerDelegate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class Main implements ConsumerDelegate, CreditScoreServiceDelegate, ProducerDelegate {

    private QueueConsumer consumer;
    private CreditScoreService service;
    private Producer producer;
    
    
    public Main() throws Exception {
        consumer = new QueueConsumer("LoanBroker9.getCreditScore_in", this);
        service = new CreditScoreService(this);
        producer = new Producer("LoanBroker9.getCreditScore_out", this);
        
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();  
    }
    

    @Override
    public void didConsumeMessageWithOptionalException(HashMap application, IOException ex) {
        System.out.println("\n{GetCreditScore} -- didConsumeMessageWithOptionalException");
        if (ex == null) { 
            System.out.println("Message: " + application.toString());
            service.getCreditScore(application); 
        } else { 
            System.out.println("Exception: " + ex.getLocalizedMessage());
        } 
    }
    
    @Override
    public void didGetCreditScoreWithOptionalException(HashMap application, Exception ex) {
        System.out.println("\n{GetCreditScore} -- didGetCreditScoreWithOptionalException");
        if (ex == null) { 
            System.out.println("Message: " + application.toString());
            producer.sendMessage(application);  
        } else { 
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }
    
    @Override
    public void didProduceMessageWithOptionalException(IOException ex) {
        System.out.println("\n{GetCreditScore} -- didProduceMessageWithOptionalException");
        if (ex == null) { 
            System.out.println("Message: success");
        } else { 
            System.out.println("Exception: " + ex.getLocalizedMessage());
        }
    }
    
    
    
    
    
    
    
    
    /**
     * @param args
     * @throws SQLException
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        new Main();
    }

}
