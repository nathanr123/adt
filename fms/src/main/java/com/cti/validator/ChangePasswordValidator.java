/**
 * 
 */
package com.cti.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cti.model.ChangePassword;
import com.cti.model.User;
import com.cti.service.UserService;

/**
 * @author nathanr_kamal
 *
 */
@Component("changePasswordFormValidator")
public class ChangePasswordValidator implements Validator {

	@Autowired
	UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {

		return ChangePassword.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "oldPassword",
				"required.oldPassword");

		ValidationUtils.rejectIfEmpty(errors, "newPassword",
				"required.newPassword");

		ValidationUtils.rejectIfEmpty(errors, "confirmPassword",
				"required.confirmPassword");

		ChangePassword chngPwd = (ChangePassword) target;

		if (!chngPwd.getNewPassword().equals(chngPwd.getConfirmPassword())) {

			errors.rejectValue("confirmPassword", "notmatch.password");
		}

		User usr = userService.getUserById(chngPwd.getUsername());

		if (!passwordEncoder.matches(chngPwd.getOldPassword(),
				usr.getPassword())) {
			errors.rejectValue("oldPassword", "notmatchold.password");
		}

	}

}
