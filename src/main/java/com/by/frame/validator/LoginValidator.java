package com.by.frame.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.frame.bo.LoginBo;

public class LoginValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return LoginBo.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		/*LoginCommod loginCommod = (LoginCommod) arg0;
		ValidationUtils.validate(errors, "username", []);*/
		
	}

}
