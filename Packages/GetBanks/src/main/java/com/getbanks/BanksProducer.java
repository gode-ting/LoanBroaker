package com.getbanks;

import java.util.HashMap;

public class BanksProducer extends ConnectionSetup {

    public BanksProducer(String queueName) {
        super(queueName);
    }
    
    public void publishMessage(HashMap<String, Integer> msgMap) {
        try {
            String value = "";
            channel.basicPublish("", endPointName, null, value.getBytes());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
