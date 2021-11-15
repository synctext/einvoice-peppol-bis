# einvoice-peppol-bis
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.owlsy/einvoice-peppol-bis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser)
![](https://img.shields.io/github/workflow/status/owlsy/einvoice-peppol-bis/einvoice-peppol-bis)

Java library for working with XML Invoices of [PEPPOL BIS Billing 3.0](https://docs.peppol.eu/poacc/billing/3.0/)

## Dependency
### Maven
```xml
<dependency>
    <groupId>pl.owlsy</groupId>
    <artifactId>einvoice-peppol-bis</artifactId>
    <version>0.0.1</version>
</dependency>
```
### Gradle
```groovy
implementation 'pl.owlsy:einvoice-peppol-bis:0.0.1'
```

## Usage

### Read XML Invoice
```java
import pl.owlsy.einvoice.peppol.PeppolBIS;
import pl.owlsy.einvoice.peppol.JAXBPeppolBIS;
import pl.owlsy.einvoice.peppol.model.Invoice;
        
PepploBIS engine = new JAXBPeppolBIS();
InputStream input = new InputSource(new FileReader("src/test/resources/testdata/Faktura_3.0_PEF_wersja_2.5.xml"));
Invoice invoice = engine.readInvoice(input);
```

### Write Invoice to XML
```java
import pl.owlsy.einvoice.peppol.PeppolBIS;
import pl.owlsy.einvoice.peppol.JAXBPeppolBIS;
import pl.owlsy.einvoice.peppol.model.Invoice;
        
PepploBIS engine = new JAXBPeppolBIS();
Invoice invoice = new Invoice();
// you can fill invoice with data here
String xml = engine.writeInvoice(invoice);
```

