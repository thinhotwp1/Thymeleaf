package com.tpb.vms.common;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlUtl {
	public static String convertObjectToXml(Object obj) throws JAXBException {
		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.marshal(obj, sw);
		// Verify XML Content
		String xmlContent = sw.toString();

		return xmlContent;
	}
	
	public static String convertObjectToXmlWithoutHeader(Object obj) throws JAXBException {
		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);

		jaxbMarshaller.marshal(obj, sw);
		// Verify XML Content
		String xmlContent = sw.toString();

		return xmlContent;
	}
}
