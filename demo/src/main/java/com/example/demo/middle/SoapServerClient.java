package com.example.demo.middle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

@Service
public class SoapServerClient {

    private static final String SOAP_ENDPOINT = "http://localhost:8081/ws";

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public Element sendSoapRequest(Element request) {

        DOMResult result = new DOMResult();

        webServiceTemplate.sendSourceAndReceiveToResult(
                SOAP_ENDPOINT,
                new DOMSource(request),
                result
        );

        Document document = (Document) result.getNode();
        return document.getDocumentElement();
    }
}
