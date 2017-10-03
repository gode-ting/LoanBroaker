package Translators;

import interfaces.MainInterface;
import interfaces.XMLTranslatorInterface;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
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

    MainInterface delegate;

    public XMLTranslator(MainInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    public void translateXml() {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element loanRequest = doc.createElement("LoanRequest");
            doc.appendChild(loanRequest);

            Element ssn = doc.createElement("ssn");
            ssn.appendChild(doc.createTextNode("xxxxxxxx-xxxx"));
            loanRequest.appendChild(ssn);

            Element creditScore = doc.createElement("creditScore");
            creditScore.appendChild(doc.createTextNode("Credit score"));
            loanRequest.appendChild(creditScore);

            Element loanAmount = doc.createElement("loanAmount");
            loanAmount.appendChild(doc.createTextNode("1000.0"));
            loanRequest.appendChild(loanAmount);

            Element loanDuration = doc.createElement("loanDuration");
            Date date = new Date();
            loanDuration.appendChild(doc.createTextNode(date.toString()));
            loanRequest.appendChild(loanAmount);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            // Output to console for testing
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            OutputStream xmlResult = result.getOutputStream();

            delegate.didProduceXml(null, SerializationUtils.serialize((Serializable) xmlResult));

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
