package Translators;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.xml.sax.SAXException;

public class XMLTranslatorTest {

    @Test
    @DisplayName("should return a translated xml document")
    public void testTranslateXml() throws SAXException, IOException, ParserConfigurationException {
        XMLTranslator xmlTranslator = new XMLTranslator();

        HashMap application = new HashMap();

        String expectedSsn = "xxxxxxx-xxxx";
        String expectedCreditScore = "500";
        String expectedLoanAmount = "1000.0";
        Date date = new Date();
        String expectedLoanDuration = date.toString();

        application.put("ssn", expectedSsn);
        application.put("creditScore", expectedCreditScore);
        application.put("loanAmount", expectedLoanAmount);
        application.put("loanDuration", expectedLoanDuration);

        // Hard coded string
        String actualResult = xmlTranslator.translateXml(application);

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(actualResult));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("LoanRequest");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element ele = (Element) nodes.item(i);

            NodeList ssn = ele.getElementsByTagName("ssn");
            Element ssnEle = (Element) ssn.item(0);
            String actualSsn = getCharacterDataFromElement(ssnEle);

            NodeList creditScore = ele.getElementsByTagName("creditScore");
            Element creditScoreEle = (Element) creditScore.item(0);
            String actualCreditScore = getCharacterDataFromElement(creditScoreEle);

            NodeList loanAmount = ele.getElementsByTagName("loanAmount");
            Element loanAmountEle = (Element) loanAmount.item(0);
            String actualLoanAmount = getCharacterDataFromElement(loanAmountEle);

            NodeList loanDuration = ele.getElementsByTagName("loanDuration");
            Element loanDurationEle = (Element) loanDuration.item(0);
            String actualLoanDuration = getCharacterDataFromElement(loanDurationEle);

            
            assertThat(actualSsn, is(expectedSsn));
            assertThat(actualCreditScore, is(expectedCreditScore));
            assertThat(actualLoanAmount, is(expectedLoanAmount));
            assertThat(actualLoanDuration, is(expectedLoanDuration));
        }
    }

    public static String getCharacterDataFromElement(Element e) {
        System.out.println("Element: " + e);
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
