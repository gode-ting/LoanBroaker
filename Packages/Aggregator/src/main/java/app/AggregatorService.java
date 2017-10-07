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
                        if (((ArrayList) message.get("banks")).size() < 1) {
                            throw new Exception();
                        }
                        unfinishedMessages.put((String)((HashMap)message.get("application")).get("ssn"),new messageCollection(((ArrayList) message.get("banks")).size(), (String)((HashMap)message.get("application")).get("ssn")));
                    } else {
                        messageCollection mc = unfinishedMessages.get((String)((HashMap)message.get("application")).get("ssn"));
                        mc.getMessages().add(message);
                        System.out.println("waiting for: " + mc.getSize() + "   has: " + mc.getMessages().size());
                    }
                    delegate.didAggregatorServiceWithOptionalException(message, null);

                } catch (Exception e) {
                    delegate.didAggregatorServiceWithOptionalException(null, e);
                }
            }
        });
        t.run();
    }
    
    class messageCollection{
        int size;
        String ssn;
        ArrayList<HashMap> messages;

        public messageCollection(int size, String ssn) {
            this.size = size;
            this.ssn = ssn;
            this.messages = new ArrayList<>();
        }

        public int getSize() {
            return size;
        }

        public String getSsn() {
            return ssn;
        }

        public ArrayList<HashMap> getMessages() {
            return messages;
        }
        
    }
}
