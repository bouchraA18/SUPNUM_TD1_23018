package com.example.demo.middle;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
@RestController
@RequestMapping("/middle/servers")
public class MiddleServerController {
    private static final String NAMESPACE = "http://example.com/demo/soap";
    @Autowired
    private SoapServerClient soapServerClient;
    @GetMapping
    public String listServers() throws Exception {

        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element request = doc.createElementNS(
                NAMESPACE,
                "ListServersRequest"
        );

        Element response = soapServerClient.sendSoapRequest(request);
        return response.getTextContent();
    }

    @GetMapping("/{id}/status")
    public String getServerStatus(@PathVariable Long id) throws Exception {

        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element request = doc.createElementNS(
                NAMESPACE,
                "GetServerStatusRequest"
        );

        Element idElement = doc.createElement("id");
        idElement.setTextContent(String.valueOf(id));
        request.appendChild(idElement);

        Element response = soapServerClient.sendSoapRequest(request);
        return response.getTextContent();
    }
}
