/**
 * 
 */
package com.cti.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cti.model.UserGroup;

/**
 * @author nathanr_kamal
 *
 */
@Component("groupValidator")
public class GroupValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UserGroup.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		UserGroup userGroup = (UserGroup) target;
		
		ValidationUtils.rejectIfEmpty(errors, "groupname", "required.groupName");
		
		if (userGroup.getPriority() == -1) {
			errors.rejectValue("priority", "required.priority");
		}

	}

}
