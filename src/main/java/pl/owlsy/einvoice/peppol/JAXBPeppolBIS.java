package pl.owlsy.einvoice.peppol;

import lombok.SneakyThrows;
import pl.owlsy.einvoice.peppol.model.Invoice;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class JAXBPeppolBIS implements PeppolBIS {
    @Override
    @SneakyThrows
    public Invoice readInvoice(InputStream input) {
        JAXBContext context = JAXBContext.newInstance(Invoice.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Invoice) unmarshaller.unmarshal(new InputStreamReader(input));
    }

    @Override
    @SneakyThrows
    public String writeInvoice(Invoice invoice) {
        JAXBContext context = JAXBContext.newInstance(Invoice.class);
        StringWriter writer = new StringWriter();
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.marshal(invoice, writer);
        return writer.toString();
    }
}
