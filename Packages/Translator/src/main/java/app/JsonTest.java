package app;

import org.json.simple.JSONObject;

public class JsonTest {

    public static void main(String[] args) {
        JSONObject response = new JSONObject();
        response.put("ssn", "xxxxxxx-xxxx");
        response.put("creditScore", 550);
        response.put("loanAmount", 1000.1);
        response.put("loanDuration", "360");
        
        System.out.println("response: " + response);
        
        // Get one property - prints: SSN: xxxxxxx-xxxx
        System.out.println("SSN: " + response.get("ssn"));
            
        String ssn = response.get("ssn").toString();
        
        System.out.println("SSN: " + ssn);
        
    }
}
