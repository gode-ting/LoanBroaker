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
        System.out.println("1111");
        String type = headers.get("type").toString();
        System.out.println("2222");
        System.out.println("Headerzzzzzzzzz - " + headers.toString());
//        String bankID = headers.get("bankID").toString();
        System.out.println("type in normalize methods: " + type);
        switch (type) {
            case "xml": {
                   
                String xml = new String(body);
                System.out.println("incoming: " + xml);
                XmlMapper xmlMapper = new XmlMapper();
                LoanResponse response = xmlMapper.readValue(xml, LoanResponse.class);
//                response.setBankID(bankID);
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(response);
                return json;

            }

            case "json": {

                String jsonString = new String(body);
                System.out.println("this is json: " + jsonString);
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(jsonString, LoanResponse.class);
//                response.setBankID(bankID);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;

            }

        }
        return "";
    }

}
