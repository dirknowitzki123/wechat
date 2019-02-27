package com.by.frame.intfc;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.core.MethodParameter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.by.frame.intfc.agrument.MapWrap;
import com.by.frame.intfc.annotation.TransBody;

public class TransBodyArgumentResolve implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(TransBody.class);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String jsonBody = (String)request.getAttribute("transBody");
		if(MapWrap.class.isAssignableFrom(parameter.getParameterType())){
			MapWrap<String,Object> wrap = (MapWrap<String, Object>) parameter.getParameterType().newInstance();
			Type mapType = wrap.getInnerMap().getClass().getGenericSuperclass();
			Map<String, Object> map = JSON.parseObject(jsonBody, new TypeReference<Map<String,Object>>(){});
			wrap.setInnerMap( map);
			return wrap;
		}
		Type paramType = parameter.getGenericParameterType();
		
		Object result = JSON.parseObject(jsonBody, paramType);
		//Object result = readObj(parameter,paramType,jsonBody);
		return result;
	}
	
	/**
	 * 将json体转成参数对象，并读取参数Object
	 * @param methodParam
	 * @param targetType
	 * @param jsonStr
	 * @return
	 * @throws HttpMediaTypeNotSupportedException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private <T> Object readObj(MethodParameter methodParam,Type targetType,String jsonStr ) throws HttpMediaTypeNotSupportedException, IOException {
		Class<?> contextClass = methodParam.getDeclaringClass();
		ObjectMapper objectMapper = new ObjectMapper(); 
		JavaType javaType = (contextClass != null) ? objectMapper.getTypeFactory().constructType(targetType, contextClass) : objectMapper.constructType(targetType);
		Object result = objectMapper.readValue(jsonStr, javaType);
		return result;
	}
	
	
	
}
