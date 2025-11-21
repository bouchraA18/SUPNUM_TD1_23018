package com.example.demo.endpoint;

import com.example.demo.model.Server;
import com.example.demo.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

@Endpoint
public class ServerEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/demo/soap";

    @Autowired
    private ServerService serverService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateServerRequest")
    @ResponsePayload
    public Element createServer(@RequestPayload Element request) throws TransformerException {
        String name = getElementValue(request, "name");
        String ipAddress = getElementValue(request, "ipAddress");
        
        Server server = new Server();
        server.setName(name);
        server.setIpAddress(ipAddress);
        
        Server createdServer = serverService.createServer(server);
        return createServerResponse(createdServer);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListServersRequest")
    @ResponsePayload
    public Element listServers(@RequestPayload Element request) throws TransformerException {
        List<Server> servers = serverService.listServers();
        return createListServersResponse(servers);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetServerStatusRequest")
    @ResponsePayload
    public Element getServerStatus(@RequestPayload Element request) throws TransformerException {
        Long id = Long.parseLong(getElementValue(request, "id"));
        Boolean status = serverService.getServerStatus(id);
        return createStatusResponse(status);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StartServerRequest")
    @ResponsePayload
    public Element startServer(@RequestPayload Element request) throws TransformerException {
        Long id = Long.parseLong(getElementValue(request, "id"));
        Server server = serverService.startServer(id);
        return createServerResponse(server);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StopServerRequest")
    @ResponsePayload
    public Element stopServer(@RequestPayload Element request) throws TransformerException {
        Long id = Long.parseLong(getElementValue(request, "id"));
        Server server = serverService.stopServer(id);
        return createServerResponse(server);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteServerRequest")
    @ResponsePayload
    public Element deleteServer(@RequestPayload Element request) throws TransformerException {
        Long id = Long.parseLong(getElementValue(request, "id"));
        try {
            serverService.deleteServer(id);
            return createDeleteResponse(true);
        } catch (Exception e) {
            return createDeleteResponse(false);
        }
    }

    // Méthodes utilitaires pour manipuler XML
    private String getElementValue(Element parent, String localName) {
        NodeList nodes = parent.getElementsByTagNameNS(NAMESPACE_URI, localName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private Element createServerResponse(Server server) throws TransformerException {
        String xml = "<CreateServerResponse xmlns=\"" + NAMESPACE_URI + "\">" +
                     "<server>" +
                     "<id>" + server.getId() + "</id>" +
                     "<name>" + server.getName() + "</name>" +
                     "<ipAddress>" + server.getIpAddress() + "</ipAddress>" +
                     "<status>" + server.getStatus() + "</status>" +
                     "</server>" +
                     "</CreateServerResponse>";
        return parseXml(xml).getDocumentElement();
    }

    private Element createListServersResponse(List<Server> servers) throws TransformerException {
        StringBuilder xml = new StringBuilder();
        xml.append("<ListServersResponse xmlns=\"").append(NAMESPACE_URI).append("\">");
        for (Server server : servers) {
            xml.append("<server>")
               .append("<id>").append(server.getId()).append("</id>")
               .append("<name>").append(server.getName()).append("</name>")
               .append("<ipAddress>").append(server.getIpAddress()).append("</ipAddress>")
               .append("<status>").append(server.getStatus()).append("</status>")
               .append("</server>");
        }
        xml.append("</ListServersResponse>");
        return parseXml(xml.toString()).getDocumentElement();
    }

    private Element createStatusResponse(Boolean status) throws TransformerException {
        String xml = "<GetServerStatusResponse xmlns=\"" + NAMESPACE_URI + "\">" +
                     "<status>" + status + "</status>" +
                     "</GetServerStatusResponse>";
        return parseXml(xml).getDocumentElement();
    }

    private Element createDeleteResponse(Boolean success) throws TransformerException {
        String xml = "<DeleteServerResponse xmlns=\"" + NAMESPACE_URI + "\">" +
                     "<success>" + success + "</success>" +
                     "</DeleteServerResponse>";
        return parseXml(xml).getDocumentElement();
    }

    private org.w3c.dom.Document parseXml(String xml) throws TransformerException {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
        } catch (Exception e) {
            throw new TransformerException("Error parsing XML", e);
        }
    }
}