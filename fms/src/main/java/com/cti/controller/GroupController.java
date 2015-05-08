/**
 * 
 */
package com.cti.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
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

import com.cti.model.GroupPermissionForComponents;
import com.cti.model.User;
import com.cti.model.UserAndGroupList;
import com.cti.model.UserGroup;
import com.cti.model.UserGroupPermission;
import com.cti.service.GroupPermissionService;
import com.cti.service.GroupService;
import com.cti.service.UserGroupListService;
import com.cti.service.UserService;

/**
 * @author nathanr_kamal
 *
 */
@Controller
@EnableWebMvcSecurity
public class GroupController {

	@Autowired
	UserService userService;

	@Autowired
	GroupService groupService;

	@Autowired
	UserGroupListService userGroupListService;

	@Autowired
	GroupPermissionService groupPermissionService;

	@Autowired
	@Qualifier("groupAssignValidator")
	Validator groupAssignValidator;

	@Autowired
	@Qualifier("groupValidator")
	Validator groupValidator;

	@InitBinder("userAndGroupListForm")
	protected void initUserBinder(WebDataBinder binder) {
		binder.setValidator(groupAssignValidator);
	}

	@RequestMapping(value = "/listgroups", method = RequestMethod.GET)
	public ModelAndView listUsers(Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("grouplist", getAllGroupsDetail());

		mav.setViewName("listgroup");

		return mav;

	}

	@RequestMapping(value = "/newgroup", method = RequestMethod.GET)
	public @ResponseBody ModelAndView goToNewUserRegistration(
			Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		UserGroup groupForm = new UserGroup();

		String id = getLatestGroupId();

		groupForm.setGroupid(id);

		model.put("groupForm", groupForm);

		mav.setViewName("group");

		return mav;
	}

	@RequestMapping(value = "/createOrModifyGroup", method = RequestMethod.POST)
	public ModelAndView doCreateNewUser(
			@ModelAttribute("groupForm") UserGroup userGroup,
			BindingResult result, Map<String, Object> model,
			SessionStatus status) {

		ModelAndView mav = new ModelAndView();

		groupValidator.validate(userGroup, result);

		if (result.hasErrors()) {

			mav.setViewName("group");

			return mav;
		} else {

			Date d = new Date();

			UserGroup gp = groupService.getGroupById(userGroup.getGroupid());

			if (gp != null) {

				gp.setModifiedtime(d);

				gp.setGroupname(userGroup.getGroupname());

				gp.setPriority(userGroup.getPriority());

				groupService.updateGroup(gp);

			} else {

				userGroup.setCreatedtime(d);

				userGroup.setModifiedtime(d);

				groupService.addGroup(userGroup);

				mav.addObject("msg", "New Group " + userGroup.getGroupname()
						+ " Created Successfully");

			}

			mav.setViewName("hello");
		}

		return mav;

	}

	@RequestMapping(value = "/loadGroupdetail", method = RequestMethod.GET)
	public ModelAndView goUserProfileupdate(
			@RequestParam("group") String group, Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		UserGroup grpdetailForm = groupService.getGroupById(group);

		model.put("groupForm", grpdetailForm);

		mav.setViewName("group");

		return mav;

	}

	@RequestMapping(value = "/deleteGroup", method = RequestMethod.GET)
	public ModelAndView deleteGroup(@RequestParam("group") String group,
			Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		groupService.deletGroup(group);

		mav.addObject("msg", "Group successfully deleted.");

		mav.addObject("grouplist", getAllGroupsDetail());

		mav.setViewName("listgroup");

		return mav;

	}

	@RequestMapping(value = "/loadUsersAndGroups", method = RequestMethod.GET)
	public @ResponseBody ModelAndView loadUsersAndGroups(
			Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("userAndGroupListForm", new UserAndGroupList());

		mav.setViewName("assignuseransgroup");

		return mav;
	}

	@RequestMapping(value = "/loadGroupPermission", method = RequestMethod.GET)
	public @ResponseBody ModelAndView loadGroupPermission(
			Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("userGroupPermissionForm",
				new GroupPermissionForComponents());

		mav.setViewName("groupandpermission");

		return mav;
	}

	@RequestMapping(value = "/assignGroupPermission", method = RequestMethod.POST)
	public @ResponseBody ModelAndView assignGroupPermission(
			@ModelAttribute("userGroupPermissionForm") GroupPermissionForComponents userGroupPermissionForm,
			BindingResult result, Map<String, Object> model) {

		List<UserGroupPermission> listGroupPermission = new ArrayList<UserGroupPermission>();

		ModelAndView mav = new ModelAndView();

		int[] per = getPermissions(userGroupPermissionForm.getPermissions());

		Date d = new Date();

		List<String> toComp = userGroupPermissionForm.getToComponent();

		List<String> toGroup = userGroupPermissionForm.getToGroup();

		for (Iterator<String> iterator = toGroup.iterator(); iterator.hasNext();) {

			String group = iterator.next();

			for (Iterator<String> iterator1 = toComp.iterator(); iterator1
					.hasNext();) {

				String comp = iterator1.next();

				listGroupPermission.add(new UserGroupPermission(group, comp,
						per, d));
			}
		}

		groupPermissionService.setGroupPerssions(listGroupPermission);

		mav.setViewName("hello");

		return mav;
	}

	@RequestMapping(value = "/assignUserandGroup", method = RequestMethod.POST)
	public @ResponseBody ModelAndView assignUsersAndGroups(
			@ModelAttribute("userAndGroupListForm") UserAndGroupList userAndGroupListForm,
			BindingResult result, Map<String, Object> model) {

		ModelAndView mav = new ModelAndView();

		groupAssignValidator.validate(userAndGroupListForm, result);

		if (result.hasErrors()) {

			mav.setViewName("assignuseransgroup");

			return mav;
		} else {

			List<String> grpList = userAndGroupListForm.getToGroup();

			List<String> userList = userAndGroupListForm.getToUser();

			userGroupListService.saveUsersandGroup(userList, grpList);

			mav.addObject("msg", "Groups are Assigned to the user");

			mav.setViewName("hello");

			return mav;
		}
	}

	@ModelAttribute("groupList")
	public Map<String, String> getGroupsList() {

		Map<String, String> groupsList = new HashMap<String, String>();

		List<UserGroup> groupList = groupService.listGroups();

		for (Iterator<UserGroup> iterator = groupList.iterator(); iterator
				.hasNext();) {

			UserGroup group = iterator.next();

			groupsList.put(group.getGroupid(), group.getGroupname());
		}

		return groupsList;
	}

	@ModelAttribute("componentList")
	public Map<String, String> getComponentsList() {

		Map<String, String> componentsList = new HashMap<String, String>();

		componentsList.put("SWITCH", "SWITCH");

		componentsList.put("USER", "USER");

		componentsList.put("GROUP", "GROUP");

		return componentsList;
	}

	@ModelAttribute("permissionList")
	public Map<String, String> getPermissionsList() {

		Map<String, String> permissionList = new HashMap<String, String>();

		permissionList.put("Create", "Create");

		permissionList.put("Modify", "Modify");

		permissionList.put("Delete", "Delete");

		permissionList.put("View", "View");

		return permissionList;
	}

	@ModelAttribute("usersList")
	public Map<String, String> getUsersList() {

		Map<String, String> usersList = new HashMap<String, String>();

		List<User> userList = userService.listUsers();

		for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			usersList.put(user.getUsername(), user.getUsername());
		}

		return usersList;
	}

	private int[] getPermissions(List<String> permissioninString) {

		int[] permissions = { 0, 0, 0, 1 };

		permissions[0] = permissioninString.contains("Create") ? 1 : 0;

		permissions[1] = permissioninString.contains("Modify") ? 1 : 0;

		permissions[2] = permissioninString.contains("Delete") ? 1 : 0;

		permissions[3] = permissioninString.contains("View") ? 1 : 0;

		return permissions;
	}

	public List<UserGroup> getAllGroupsDetail() {

		List<UserGroup> groups = groupService.listGroups();

		return groups;
	}

	@ModelAttribute("priorityLevel")
	public Map<Integer, Integer> getPriority() {

		Map<Integer, Integer> userPriorty = new HashMap<Integer, Integer>();

		for (int i = 5; i > 0; i--) {
			userPriorty.put(i, i);
		}

		return userPriorty;
	}

	private String getLatestGroupId() {
		int iddigit = 0;

		String latId = groupService.getLatestGroupID();

		if (latId != null)
			iddigit = Integer.valueOf(latId.substring(3, latId.length()));

		iddigit++;

		String str = String.format("GRP%04d", iddigit);

		return str;

	}
}
