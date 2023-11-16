package com.tpb.vms.common;

import javax.xml.stream.XMLStreamException;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.MessageContextListener;

public class MessageContextListenerImpl implements MessageContextListener {
	
	
	public void attachServiceContextEvent(ServiceContext sc, MessageContext mc) {
	}

	public void attachEnvelopeEvent(MessageContext mc) {
		try {
			mc.getEnvelope().cloneOMElement().serialize(System.out);
		} catch (XMLStreamException e) {
		}
	}
}
