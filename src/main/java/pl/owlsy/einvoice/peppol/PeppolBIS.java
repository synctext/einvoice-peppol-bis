package pl.owlsy.einvoice.peppol;

import pl.owlsy.einvoice.peppol.model.Invoice;

import java.io.InputStream;

public interface PeppolBIS {
    Invoice readInvoice(InputStream input);

    String writeInvoice(Invoice invoice);
}
