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
    HashMap<String, messageCollection> unfinishedMessages;

    public AggregatorService(AggregatorServiceDelegate delegate) {
        this.delegate = delegate;
        unfinishedMessages = new HashMap();
    }

    public void Aggregate(final HashMap message) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(message.toString());
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
                        messageCollection mc = new messageCollection(((ArrayList) message.get("banks")).size(), (String) ((HashMap) message.get("application")).get("ssn"));
                        unfinishedMessages.put((String) ((HashMap) message.get("application")).get("ssn"), mc);
                        System.out.println("threadID1: " + Thread.currentThread().toString());
                        mc.t.start();
                        
                    } else {
                        messageCollection mc = unfinishedMessages.get((String) message.get("ssn"));
                        mc.getMessages().add(message);
                        System.out.println("---- New message from bank ----");
                        System.out.println("");
                        System.out.println("SSN: " + (String) message.get("ssn"));
                        System.out.println("Bank id: " + (String) message.get("bankID"));
                        System.out.println("Message no. " + mc.getMessages().size() + "/" + mc.getSize());
                        System.out.println("");
                        
                        if (mc.getSize() == mc.getMessages().size()) {
                            System.out.println("---- Message send to user ----");
                            System.out.println("SSN: " + (String) message.get("ssn"));
                            delegate.didAggregatorServiceWithOptionalException(mc.getBestInterest(), null);
                            mc.t.messageAlreadySent = true;
                            unfinishedMessages.remove((String) message.get("ssn"));
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

    class messageCollection {

        int size;
        String ssn;
        ArrayList<HashMap> messages;
        timerThread t;

        public messageCollection(int size, String ssn) {
            this.size = size;
            this.ssn = ssn;
            this.messages = new ArrayList();
            t = new timerThread(ssn,delegate,this);
        }

        public HashMap getBestInterest() {
            HashMap best = new HashMap();
            best.put("interestRate", 999.9d);

            for (int i = 0; i < messages.size(); i++) {
                if ((double) messages.get(i).get("interestRate") < (double) best.get("interestRate")) {
                    best = messages.get(i);
                }
            }
            return best;
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

    class timerThread extends Thread{

        int timeInSeconds = 10;
        String ssn;
        AggregatorServiceDelegate delegate;
        boolean messageAlreadySent = false;
        messageCollection mc;

        public timerThread(String ssn, AggregatorServiceDelegate delegate,messageCollection mc) {
            this.ssn = ssn;
            this.delegate = delegate;
            this.mc = mc;
        }

        @Override
        public void run() {
            try {
                System.out.println("threadID2: " + Thread.currentThread().toString());
                this.sleep(3000 * timeInSeconds);
                if (!messageAlreadySent) {
                    delegate.didAggregatorServiceWithOptionalException(mc.getBestInterest(), null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
