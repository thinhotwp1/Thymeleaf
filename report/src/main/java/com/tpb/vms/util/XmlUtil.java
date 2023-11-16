/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Viet Do-Van
 */
public class XmlUtil {

    private XmlUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static byte[] asByteArray(Document doc, String encoding) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        return writer.getBuffer().toString().getBytes();
    }

    public static String getNodeValueOrTextContent(Node node) {
        if (Objects.isNull(node)) {
            return null;
        }
        switch (node.getNodeType()) {
            case Node.ATTRIBUTE_NODE:
                return node.getNodeValue();
            case Node.ELEMENT_NODE:
                return node.getTextContent();
            default:
                return null;
        }
    }

    public static void setNodeValueOrTextContent(Node node, String nodeValueOrTextContent) {
        switch (node.getNodeType()) {
            case Node.ATTRIBUTE_NODE:
                node.setNodeValue(nodeValueOrTextContent);
                break;
            case Node.ELEMENT_NODE:
                node.setTextContent(nodeValueOrTextContent);
                break;
            default:
                break;
        }
    }

    public static Document toDocument(String xmlString) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
    }

    public static Document toDocument(byte[] xmlData) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(new ByteArrayInputStream(xmlData));
    }

    public static String contextToString(Context ctx) {
        StringBuilder sb = new StringBuilder();
        ctx.getVariableNames().forEach(variableName -> {
            sb
                    .append(variableName)
                    .append(":")
                    .append(ctx.getVariable(variableName))
                    .append(",");
        });
        return sb.toString();
    }
}
