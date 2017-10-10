/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang.SerializationUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author emilgras
 */
public class Normalizer {

    public Normalizer() {

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
