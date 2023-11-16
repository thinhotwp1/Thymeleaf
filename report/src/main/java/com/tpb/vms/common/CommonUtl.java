package com.tpb.vms.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpb.vms.security.TokenProvider;

import io.jsonwebtoken.Claims;

@Service
public class CommonUtl {
	
	private SpringTemplateEngine templateEngine;
	

	
	@Autowired
	public CommonUtl(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public Context getContext(String jsonData) throws JsonMappingException, JsonProcessingException {
		Context context = new Context();
		Map<String, Object> data = new ObjectMapper().readValue(jsonData, Map.class);
		context.setVariable("data", data);
		return context;
	}

	
	public String loadAndFillTemplate(String tempName, Context context) {
		return templateEngine.process(tempName, context);
	}
	
	public static String getUserId(String token) {
		TokenProvider tokenProvider = new TokenProvider();
		String userId = "";
		Claims user = null;

		user = tokenProvider.parseClaimsJwt(token.substring(7));
		if (user != null) {
			userId = user.getSubject();
			if (userId.contains("\\")) {
				userId = userId.substring(userId.lastIndexOf("\\") + 1);
			}

		}
		return userId;
	}
}
