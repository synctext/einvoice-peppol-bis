package pl.owlsy.einvoice.peppol;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import pl.owlsy.einvoice.peppol.model.ID;
import pl.owlsy.einvoice.peppol.model.Invoice;
import pl.owlsy.einvoice.peppol.model.ObjectFactory;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JAXBPeppolBISTest {
    private final XPath xpath = XPathFactory.newInstance().newXPath();

    @BeforeEach
    void setUp() {
        xpath.setNamespaceContext(createNamespaceContext());
    }

    @Test
    void testReadInvoice() {
        // Given
        JAXBPeppolBIS peppol = new JAXBPeppolBIS();
        InputStream stream = getInvoiceXmlInputStream();

        // When
        Invoice invoice = peppol.readInvoice(stream);

        // Then
        assertEquals(sourceXPath("//cbc:ID"), invoice.getID().getValue());
        assertEquals(sourceXPath("//cbc:CustomizationID"), invoice.getCustomizationID());
        assertEquals(sourceXPath("//cac:InvoicePeriod/cbc:StartDate"), invoice.getInvoicePeriod().getStartDate().toXMLFormat());
    }

    @Test
    @SneakyThrows
    void testWriteInvoice() {
        // Given
        JAXBPeppolBIS peppol = new JAXBPeppolBIS();
        ObjectFactory factory = new ObjectFactory();
        Invoice invoice = new Invoice();
        {
            ID id = factory.createID();
            id.setValue("TestInvoice_1");
            invoice.setID(id);
        }

        // When
        Document xml = createDocument(peppol.writeInvoice(invoice));

        // Then
        assertEquals("TestInvoice_1", xpath.evaluate("//ID", xml));
    }

    @SneakyThrows
    private Document createDocument(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    @SneakyThrows
    private String sourceXPath(String source) {
        return xpath.compile(source).evaluate(new InputSource(getInvoiceXmlInputStream()));
    }

    private NamespaceContext createNamespaceContext() {
        return new NamespaceContext() {
            public String getNamespaceURI(String prefix) {
                if (prefix == null) throw new NullPointerException();
                else if ("cbc".equals(prefix))
                    return "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
                else if ("cac".equals(prefix))
                    return "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
                return "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
            }

            public String getPrefix(String uri) {
                throw new UnsupportedOperationException();
            }

            public Iterator getPrefixes(String uri) {
                throw new UnsupportedOperationException();
            }
        };
    }

    private InputStream getInvoiceXmlInputStream() {
        return getClass().getClassLoader().getResourceAsStream("testdata/Faktura_3.0_PEF_wersja_2.5.xml");
    }
}