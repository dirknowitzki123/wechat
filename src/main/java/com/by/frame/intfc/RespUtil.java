package com.by.frame.intfc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class RespUtil {

	/**
	 * resp写json
	 * @param param
	 * @param resp
	 */
	public static String writeJson(Map<String,Object> param,HttpServletResponse resp){
		String jsonStr = JSON.toJSONString(param);
		return writeJson(jsonStr,resp);
	}

	/**
	 * resp写json信息
	 * @param code
	 * @param msg
	 */
	public static String writeCode(String code,String msg,HttpServletResponse resp){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("code", code);
		param.put("msg", msg);
		return writeJson(param,resp);
	}
	
	
	public static String writeJson(String jsonStr,HttpServletResponse resp){
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter writer = resp.getWriter();
			writer.print(jsonStr);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	
}
