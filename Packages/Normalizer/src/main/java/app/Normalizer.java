/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author emilgras
 */
public class Normalizer {

    public Normalizer() throws FileNotFoundException, IOException {
        
    }

    public String normalize(byte[] body, Map headers) throws UnsupportedEncodingException, IOException {
        String type = headers.get("type").toString();
        System.out.println("----------------------");
        System.out.println("Headerzzzzzzzzz - " + headers.toString());
        System.out.println("type in normalize methods: " + type);
        String bankID = headers.get("bankID").toString();
        switch (bankID) {
            case "bankXML": {
                   
                String xml = new String(body);
                System.out.println("incoming: " + xml);
                XmlMapper xmlMapper = new XmlMapper();
                LoanResponse response = xmlMapper.readValue(xml, LoanResponse.class);
                response.setBankID(bankID);
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(response);
                System.out.println("json: " + json);
                return json;
            }

            case "bankJSON": {

                String jsonString = new String(body);
                System.out.println("bankJSON: " + jsonString);
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(jsonString, LoanResponse.class);
                response.setBankID(bankID);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;

            }
            
            case "TingGodRabbitMQBank": {

                String jsonString = SerializationUtils.deserialize(body).toString();
                System.out.println("TingGodRabbitMQBank: " + jsonString);
                System.out.println(SerializationUtils.deserialize(body));
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(jsonString, LoanResponse.class);
                response.setBankID(bankID);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;

            }
            
            case "SoapBank": {
                
                System.out.println("Normalizer - SoapBank");
                String jsonString = SerializationUtils.deserialize(body).toString();
                System.out.println(".SoapBank: " + jsonString);
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(jsonString, LoanResponse.class);
                response.setBankID(bankID);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;
                
            }

        }
        return "";
    }

}
