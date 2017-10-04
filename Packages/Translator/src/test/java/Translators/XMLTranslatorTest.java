package Translators;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class XMLTranslatorTest {

    @Test
    @DisplayName("should return a translated xml document")
    public void testTranslateXml() {
        XMLTranslator xmlTranslator = new XMLTranslator();

        HashMap application = new HashMap();

        String expectedSsn = "xxxxxxx-xxxx";
        String expectedCreditScore = "500";
        String expectedLoanAmount = "1000.0";
        String expectedLoanDuration = "xxxx-xx-xx";

        application.put("ssn", expectedSsn);
        application.put("creditScore", expectedCreditScore);
        application.put("loanAmount", expectedLoanAmount);
        application.put("loanDuration", expectedLoanDuration);
        
        Writer sw = new StringWriter();
        //XMLStreamWriter writer = new JsonXMLOutputFactory().createXMLStreamWriter(sw);
        
        // Hard coded string
        String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><LoanRequest><ssn>xxxxxxx-xxxx</ssn><creditScore>500</creditScore><loanAmount>1000.0</loanAmount><xxxx-xx-xx>Wed Oct 04 10:15:49 CEST 2017</xxxx-xx-xx></LoanRequest>";
        OutputStream actualResult = xmlTranslator.translateXml(application);
        System.out.println("actual result: " + actualResult.toString());
        assertThat(actualResult,is(expectedResult));
    }
}
