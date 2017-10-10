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
import java.util.Properties;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author emilgras
 */
public class Normalizer {

    public Normalizer() throws FileNotFoundException, IOException {
        
    }

    public String normalize(byte[] body, String type) throws UnsupportedEncodingException, IOException {
        System.out.println("type in normalize methods: " + type);
        switch (type) {
            case "xml": {
                   
                String xml = new String(body);
                XmlMapper xmlMapper = new XmlMapper();
                LoanResponse response = xmlMapper.readValue(xml, LoanResponse.class);
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(response);
                return json;

            }

            case "json": {

                Object data = SerializationUtils.deserialize(body);
                ObjectMapper mapper = new ObjectMapper();
                LoanResponse response = mapper.readValue(data.toString(), LoanResponse.class);
                String responseJson = mapper.writeValueAsString(response);
                return responseJson;

            }

        }
        return "";
    }

}
