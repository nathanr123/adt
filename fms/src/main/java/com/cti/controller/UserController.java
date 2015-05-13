package com.cti.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cti.model.ChangePassword;
import com.cti.model.User;
import com.cti.model.UserDetail;
import com.cti.model.UserUploads;
import com.cti.service.UserDetailsService;
import com.cti.service.UserService;
import com.cti.service.UserUploadService;

@Controller
@EnableWebMvcSecurity
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserDetailsService userDetailService;

	@Autowired
	UserUploadService userUploadService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("userFormValidator")
	Validator userValidator;

	@Autowired
	@Qualifier("userDetailFormValidator")
	Validator userDetailValidator;

	@Autowired
	@Qualifier("changePasswordFormValidator")
	Validator changePasswordValidator;

	@InitBinder("userForm")
	protected void initUserBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	@InitBinder("userdetailForm")
	protected void initUserDetailBinder(WebDataBinder binder) {
		binder.setValidator(userDetailValidator);
	}

	@InitBinder("changePasswordForm")
	protected void initChangePasswordBinder(WebDataBinder binder) {
		binder.setValidator(changePasswordValidator);
	}

	@RequestMapping(value = "/newuser", method = RequestMethod.GET)
	public @ResponseBody ModelAndView goToNewUserRegistration(Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		User userForm = new User();

		model.put("userForm", userForm);

		mav.setViewName("user");

		return mav;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public @ResponseBody ModelAndView goToChangePassword(Principal principal, Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		ChangePassword changePasswordForm = new ChangePassword();

		changePasswordForm.setUsername(principal.getName());

		model.put("changePasswordForm", changePasswordForm);

		mav.setViewName("changepassword");

		return mav;
	}

	@RequestMapping(value = "/doChangePassword", method = RequestMethod.POST)
	public @ResponseBody ModelAndView doChangePassword(
			@ModelAttribute("changePasswordForm") ChangePassword changePasswordForm, BindingResult result,
			Map<String, Object> model, SessionStatus status) {

		ModelAndView mav = new ModelAndView();

		changePasswordValidator.validate(changePasswordForm, result);

		User usr = userService.getUserById(changePasswordForm.getUsername());

		if (result.hasErrors()) {

			mav.setViewName("changepassword");
		}

		else {

			usr.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));

			usr.setModifiedtime(new Date());

			userService.updateUser(usr);

			mav.addObject("msg", "Password Changed Successfully");

			mav.setViewName("hello");
		}

		return mav;
	}

	@RequestMapping(value = "/createnewuser", method = RequestMethod.POST)
	public ModelAndView doCreateNewUser(@ModelAttribute("userForm") User user, BindingResult result,
			Map<String, Object> model, SessionStatus status) {

		ModelAndView mav = new ModelAndView();

		userValidator.validate(user, result);

		if (result.hasErrors()) {

			mav.setViewName("user");

			return mav;
		} else {
			Date d = new Date();

			user.setPassword(user.getPassword());

			user.setCreatedtime(d);

			user.setModifiedtime(d);

			// user.setUserrole("ROLE_USER");

			userService.saveUser(user);

			UserDetail userdetailForm = new UserDetail();

			userdetailForm.setUsername(user.getUsername());

			model.put("userdetailForm", userdetailForm);

			mav.addObject("msg", "New User " + user.getUsername() + " Created Successfully");

			mav.setViewName("userdetail");

			return mav;
		}

	}

	@RequestMapping(value = "/loadUserdetail", method = RequestMethod.GET)
	public ModelAndView goUserProfileupdate(@RequestParam("user") String user, Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		UserDetail userdetailForm = userDetailService.getUserDetailById(user);

		if (userdetailForm == null) {

			userdetailForm = new UserDetail();

			userdetailForm.setUsername(user);
		}

		model.put("userdetailForm", userdetailForm);

		mav.setViewName("userdetail");

		return mav;

	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam("user") String user, Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		if (!userService.removeUser(user)) {

			mav.addObject("msg", "Unable to delete " + user + ".");
		} else {
			mav.addObject("msg", user + " successfully deleted.");
		}
		mav.addObject("userlist", getAllUsersDetail());

		mav.setViewName("listuser");

		return mav;

	}

	@RequestMapping(value = "/updateuserdetail", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(@ModelAttribute("userdetailForm") UserDetail userDetail,
			BindingResult result, Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		userDetailValidator.validate(userDetail, result);

		if (result.hasErrors()) {

			mav.setViewName("userdetail");

			return mav;
		} else {

			String view = "listuser";

			String msg = "";

			Date d = new Date();

			userDetail.setCreatedtime(d);

			userDetail.setModifiedtime(d);

			if (userDetailService.isUserProfileAlreadyAvailable(userDetail.getUsername())) {
				userDetailService.updateUserDetail(userDetail);

				view = "hello";

				msg = "Your Profile Updated Successfully !!";

			} else {

				userDetailService.saveUserDetail(userDetail);

				msg = userDetail.getFullname() + " Profile Updated Successfully !!";

				mav.addObject("userlist", getAllUsersDetail());
			}

			mav.addObject("msg", msg);

			mav.setViewName(view);

			return mav;
		}
	}

	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
	public ModelAndView listUsers(Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("userlist", getAllUsersDetail());

		mav.setViewName("listuser");

		return mav;

	}

	@RequestMapping(value = "/listfiles", method = RequestMethod.GET)
	public ModelAndView listFiles(Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("fileslist", getAllUserFiles());

		mav.setViewName("listfile");

		return mav;

	}

	public List<UserUploads> getAllUserFiles() {

		return userUploadService.listAllUploads();
	}

	public List<UserDetail> getAllUsersDetail() {
		List<UserDetail> userDetailList = new ArrayList<UserDetail>();

		List<User> users = userService.listUsers();

		UserDetail ud = null;

		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {

			User user = iterator.next();

			ud = getUserDetail(user.getUsername());

			if (ud == null) {
				ud = new UserDetail();

				ud.setUsername(user.getUsername());

				ud.setCreatedtime(user.getCreatedtime());

				ud.setModifiedtime(user.getModifiedtime());
			}
			userDetailList.add(ud);
		}

		return userDetailList;
	}

	private UserDetail getUserDetail(String username) {
		return userDetailService.getUserDetailById(username);
	}
}
