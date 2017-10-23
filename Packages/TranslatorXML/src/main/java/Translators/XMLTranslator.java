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
        String xmlResult = null;
        try {
            String ssn = (String) application.get("ssn");
            long creditScore = (long) application.get("creditScore");
            double loanAmount = (double) application.get("loanAmount");
            long loanDuration = (long) application.get("loanDuration");

            Instant instant = Instant.ofEpochSecond(loanDuration * 365 * 24 * 60 * 60);
            Date myDate = Date.from(instant);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S z");
            String formattedDate = formatter.format(myDate);
            
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
            loanDurationEle.appendChild(doc.createTextNode(formattedDate));
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
        return xmlResult;
    }
}
