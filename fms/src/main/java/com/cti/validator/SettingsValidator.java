package com.cti.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cti.model.SaveSettings;
import com.cti.service.UserService;

@Component("savesettingsFormValidator")
public class SettingsValidator implements Validator {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {

		return SaveSettings.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "foldername", "required.foldername");

		ValidationUtils.rejectIfEmpty(errors, "folderpath", "required.folderpath");

	}

}
