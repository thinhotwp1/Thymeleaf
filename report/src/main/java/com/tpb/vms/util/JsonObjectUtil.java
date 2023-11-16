package com.tpb.vms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonObjectUtil {
	public static String convertObjectToJson(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String requestBodyJson = null;
		try {
			requestBodyJson = ow.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.debug("parse billReqBodyDTO to json string error");
			e.printStackTrace();
		}
		return requestBodyJson;
	}
}
