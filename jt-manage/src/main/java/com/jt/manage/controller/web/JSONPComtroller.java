package com.jt.manage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

@Controller
public class JSONPComtroller {
	@RequestMapping(value="/web/testJSONP", produces="html/text;charset=utf-8")
	@ResponseBody
	public String jsonp(String callback) throws JsonProcessingException{
		User user = new User();
		user.setId(1);
		user.setName("白杰");
		user.setAge(2);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(user);
		return callback+"("+json+")";
	}
}
