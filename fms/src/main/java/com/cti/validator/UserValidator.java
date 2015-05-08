/**
 * 
 */
package com.cti.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cti.model.User;

/**
 * @author nathanr_kamal
 *
 */

@Component("userFormValidator")
public class UserValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "username", "required.userName");

		ValidationUtils.rejectIfEmpty(errors, "password", "required.password");

		ValidationUtils.rejectIfEmpty(errors, "confirmPassword",
				"required.confirmPassword");

		User user = (User) target;

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("password", "notmatch.password");
		}

		if (user.getPriority() == -1) {
			errors.rejectValue("priority", "required.priority");
		}
	}

}
