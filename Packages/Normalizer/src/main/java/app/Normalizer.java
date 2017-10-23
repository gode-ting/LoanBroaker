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
        String bankID = headers.get("bankID").toString();
        switch (bankID) {
            case "bankXML": {
                   
                String xml = new String(body);
                XmlMapper xmlMapper = new XmlMapper();
                LoanResponse response = xmlMapper.readValue(xml, LoanResponse.class);
                response.setBankID(bankID);
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(response);
                return json;
            }

            case "bankJSON": {

                String jsonString = new String(body);
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(jsonString, LoanResponse.class);
                response.setBankID(bankID);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;

            }
            
            case "TingGodRabbitMQBank": {

                String jsonString = SerializationUtils.deserialize(body).toString();
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(jsonString, LoanResponse.class);
                response.setBankID(bankID);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;

            }
            
            case "SoapBank": {
                
                String jsonString = SerializationUtils.deserialize(body).toString();
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
