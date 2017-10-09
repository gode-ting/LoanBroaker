/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import interfaces.AggregatorServiceDelegate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Frederik
 */
public class AggregatorService {

    AggregatorServiceDelegate delegate;
    HashMap<String,messageCollection> unfinishedMessages;

    public AggregatorService(AggregatorServiceDelegate delegate) {
        this.delegate = delegate;
        unfinishedMessages = new HashMap();
    }

    public void Aggregate(final HashMap message) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (message.containsKey("banks")) {
                        System.out.println("---- New application added waiting queue----");
                        System.out.println("");
                        System.out.println("SSN: " + (String)((HashMap)message.get("application")).get("ssn"));
                        System.out.println("Waiting for " + ((ArrayList) message.get("banks")).size() + " banks...");
                        System.out.println("");
                        if (((ArrayList) message.get("banks")).size() < 1) {
                            throw new Exception();
                        }
                        unfinishedMessages.put((String)((HashMap)message.get("application")).get("ssn"),new messageCollection(((ArrayList) message.get("banks")).size(), (String)((HashMap)message.get("application")).get("ssn")));
                    } else {
                        messageCollection mc = unfinishedMessages.get((String)message.get("ssn"));
                        System.out.println("." + mc);
                        System.out.println("." + mc.getMessages().size());
                        System.out.println("." + message);
                        mc.getMessages().put("test" + mc.getMessages().size(), message);
                        System.out.println("---- New message from bank ----");
                        System.out.println("");
                        System.out.println("SSN: " + (String)message.get("ssn"));
                        System.out.println("Message no. " + mc.getMessages().size() + "/" + mc.getSize());
                        System.out.println("");
                        if(mc.getSize() == mc.getMessages().size()){
                            System.out.println("---- Message send to user ----");
                            System.out.println("SSN: " + (String)message.get("ssn"));
                            delegate.didAggregatorServiceWithOptionalException(mc.getMessages(), null);
                            unfinishedMessages.remove((String)message.get("ssn"));
                        }
                    }
                    

                } catch (Exception e) {
                    e.printStackTrace();
                    delegate.didAggregatorServiceWithOptionalException(null, e);
                }
            }
        });
        t.run();
    }
    
    class messageCollection{
        int size;
        String ssn;
        HashMap messages;

        public messageCollection(int size, String ssn) {
            this.size = size;
            this.ssn = ssn;
            this.messages = new HashMap();
        }

        public int getSize() {
            return size;
        }

        public String getSsn() {
            return ssn;
        }

        public HashMap getMessages() {
            return messages;
        }
        
    }
}
