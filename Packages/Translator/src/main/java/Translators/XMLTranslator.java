package Translators;

import interfaces.MainInterface;
import interfaces.XMLTranslatorInterface;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.SerializationUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTranslator implements XMLTranslatorInterface {

    public XMLTranslator() {

    }

    @Override
    public String translateXml(HashMap application) {
        String xmlResult = null;
        try {
            String ssn = (String) application.get("ssn");
            String creditScore = (String) application.get("creditScore");
            String loanAmount = (String) application.get("loanAmount");
            String loanDuration = (String) application.get("loanDuration");
            System.out.println("Ssn: " + ssn + " - credit score: " + creditScore + " - loan amount: " + loanAmount + " - loan duration: " + loanDuration);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element loanRequest = doc.createElement("LoanRequest");
            doc.appendChild(loanRequest);

            Element ssnEle = doc.createElement("ssn");
            ssnEle.appendChild(doc.createTextNode(ssn));
            loanRequest.appendChild(ssnEle);

            Element creditScoreEle = doc.createElement("creditScore");
            creditScoreEle.appendChild(doc.createTextNode(creditScore));
            loanRequest.appendChild(creditScoreEle);

            Element loanAmountEle = doc.createElement("loanAmount");
            loanAmountEle.appendChild(doc.createTextNode(loanAmount));
            loanRequest.appendChild(loanAmountEle);

            Element loanDurationEle = doc.createElement(loanDuration);
            Date date = new Date();
            loanDurationEle.appendChild(doc.createTextNode(date.toString()));
            loanRequest.appendChild(loanDurationEle);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            // Output to console for testing
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);
            System.out.println("String writer: " + stringWriter.toString());
            xmlResult = stringWriter.toString();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlResult;
    }
}
