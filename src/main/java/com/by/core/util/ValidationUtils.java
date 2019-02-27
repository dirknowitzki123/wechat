package com.by.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public class ValidationUtils extends org.springframework.validation.ValidationUtils{
	
	protected static final Log logger = LogFactory.getLog(ValidationUtils.class);
	
	public static final Integer isDigits = 0;
	public static final Pattern isDigitsPattern= Pattern.compile("^\\d+$");
	public static final String isDigitsErrorCode = "error.isDigits";
	
	private static final Map<String, String> defaultMessages = new HashMap<String, String>();
	
	public static void validate( Errors errors, String field, Collection<Integer> rules ) {
		validate( errors, field, rules, null );
	}
	
	public static void validate(Errors errors, String field, Collection<Integer> rules, Map<String, String> messages ){
		Assert.notNull(errors, "Errors object must not be null");
		if ( messages == null ) {
			messages = defaultMessages;
		}
		for (Integer rule : rules) {
			
			if ( rule == isDigits ) {
				isDigits( errors, field, null, messages.get( field ) );
				if ( errors.getFieldErrorCount( field ) > 0 ) {
					break;
				}
			}
			
			
		}
	}
	
	public static void isDigits(Errors errors, String field, String errorCode, String defaultMessage) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		Boolean valid = false;
		if ( value != null ) {
			valid = isDigitsPattern.matcher( value.toString() ).find();
		}
		if ( !valid ) {
			if ( StringUtils.isEmpty(defaultMessage) ) {
				errorCode = isDigitsErrorCode;
			}
			errors.rejectValue(field, errorCode, defaultMessage);
		}
	}
	
	
}
