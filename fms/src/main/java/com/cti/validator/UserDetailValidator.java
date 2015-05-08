/**
 * 
 */
package com.cti.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cti.model.UserDetail;

/**
 * @author nathanr_kamal
 *
 */
@Component("userDetailFormValidator")
public class UserDetailValidator implements Validator {

	private enum MOBILE_NUMBER_ERR {
		VALID, LENGTH, NONUMBERFORMAT, EMPTY
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UserDetail.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		// ValidationUtils.rejectIfEmpty(errors, "username",
		// "required.userName");

		ValidationUtils.rejectIfEmpty(errors, "fullname", "required.fullname");

		ValidationUtils.rejectIfEmpty(errors, "mailid", "required.mailid");

		ValidationUtils.rejectIfEmpty(errors, "mobileno", "required.mobileno");

		UserDetail userDetail = (UserDetail) target;

		MOBILE_NUMBER_ERR err = isMobileNumber(userDetail.getMobileno());

		if (err == MOBILE_NUMBER_ERR.EMPTY || err == MOBILE_NUMBER_ERR.LENGTH
				|| err == MOBILE_NUMBER_ERR.NONUMBERFORMAT) {
			errors.rejectValue("mobileno", "notvalid.mobileno");
		}
	}

	private MOBILE_NUMBER_ERR isMobileNumber(String in) {

		MOBILE_NUMBER_ERR ret = MOBILE_NUMBER_ERR.VALID;

		if (in.length() == 0) {
			ret = MOBILE_NUMBER_ERR.EMPTY;
		} else if (in.length() != 10) {
			ret = MOBILE_NUMBER_ERR.LENGTH;
		} else {
			try {
				Long.parseLong(in);
			} catch (NumberFormatException nfe) {
				ret = MOBILE_NUMBER_ERR.NONUMBERFORMAT;
			}
		}

		return ret;
	}

	private boolean isValidMailid(String mailid) {

		// EmailValidator.getInstance().isValid(mailid)

		return false;
	}
}
