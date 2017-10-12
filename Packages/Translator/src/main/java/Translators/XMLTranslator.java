package Translators;

import interfaces.XMLTranslatorInterface;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTranslator implements XMLTranslatorInterface {

    public XMLTranslator() {

    }

    @Override
    public String translateXml(JSONObject application) {
        System.out.println("hallo?");
        String xmlResult = null;
        try {
            System.out.println("MOBMO: " + application);
            String ssn = (String) application.get("ssn");
            long creditScore = (long) application.get("creditScore");
            double loanAmount = (double) application.get("loanAmount");
            String loanDuration = (String) application.get("loanDuration");

            

            System.out.println("ssn: " + ssn);
            System.out.println("cr editScore: " + creditScore);
            System.out.println("loanAmount: " + loanAmount);
            System.out.println("loanDuration: " + loanDuration);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element loanRequest = doc.createElement("LoanRequest");
            doc.appendChild(loanRequest);

            Element ssnEle = doc.createElement("ssn");
            ssnEle.appendChild(doc.createTextNode(ssn));
            loanRequest.appendChild(ssnEle);

            Element creditScoreEle = doc.createElement("creditScore");
            creditScoreEle.appendChild(doc.createTextNode(creditScore + ""));
            loanRequest.appendChild(creditScoreEle);

            Element loanAmountEle = doc.createElement("loanAmount");
            loanAmountEle.appendChild(doc.createTextNode(loanAmount + ""));
            loanRequest.appendChild(loanAmountEle);

            Element loanDurationEle = doc.createElement("loanDuration");
            loanDurationEle.appendChild(doc.createTextNode(loanDuration));
            loanRequest.appendChild(loanDurationEle);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            // Output to console for testing
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);

            xmlResult = stringWriter.toString();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("slut?");
        return xmlResult;
    }
}
