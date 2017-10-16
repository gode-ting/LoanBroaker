package app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonTest {

    public static void main(String[] args) throws ParseException {
        JSONObject response = new JSONObject();
        response.put("ssn", "xxxxxxx-xxxx");
        response.put("creditScore", 550);
        response.put("loanAmount", 1000.1);
        response.put("loanDuration", "360");

        String jsonString = "{\"loanDuration\":\"360\",\"creditScore\":550,\"loanAmount\":1000.1,\"ssn\":\"xxxxxxx-xxxx\"}";

        System.out.println("response: " + response.toString());

        // Get one property - prints: SSN: xxxxxxx-xxxx
        System.out.println("SSN: " + response.get("ssn"));

        // Save to a variable and print - same print as above.
        String ssn = response.get("ssn").toString();
        System.out.println("SSN: " + ssn);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);
        
        System.out.println("Parsed json object: " + json);
        
        System.out.println("Get parsed json ssn: " + json.get("ssn"));

    }
}
