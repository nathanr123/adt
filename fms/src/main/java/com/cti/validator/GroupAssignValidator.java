/**
 * 
 */
package com.cti.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cti.model.UserAndGroupList;

/**
 * @author nathanr_kamal
 *
 */
@Component("groupAssignValidator")
public class GroupAssignValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserAndGroupList.class.isAssignableFrom(clazz);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		UserAndGroupList userAndGroupList = (UserAndGroupList) target;

		if (userAndGroupList.getToGroup() != null) {
			if (userAndGroupList.getToGroup().size() > 0) {
			} else {
				errors.rejectValue("toGroup", "noneselected.group");
			}
		} else {
			errors.rejectValue("toGroup", "noneselected.group");
		}

		if (userAndGroupList.getToUser() != null) {
			if (userAndGroupList.getToUser().size() > 0) {
			} else {
				errors.rejectValue("toUser", "noneselected.user");
			}
		} else {
			errors.rejectValue("toUser", "noneselected.user");
		}
	}

}
