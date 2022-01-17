package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
	private final UserService userService;
	private final RoleService roleService;
	@Autowired
	public UserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping({"", "login"})
	public String getLoginPage() {
		return "login";
	}

	@GetMapping(value = "/HelloUser")
	public String getUserPage (@CurrentSecurityContext(expression = "authentication.principal")
									   User principal, Model model) {
		model.addAttribute("user", principal);
		return "userINFO";
	}

	@GetMapping(value = "/admin")
	public String getUsers(Model model, Principal principal, @ModelAttribute("flashMessage") String flashAttribute, @ModelAttribute("user") User user){
		model.addAttribute("user", userService.getAllUsers());
		model.addAttribute("roles", roleService.getAllRoles());
		UserDetails user1 = userService.loadUserByUsername(principal.getName());
		model.addAttribute("user1", user1);
		return "user-list";
	}

	@GetMapping(value = "/user-create")
	public String createUsersFrom(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		return "user-create";
	}

	@PostMapping (value = "/user-create")
	public String createUsers(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@GetMapping("user-delete/{id}")
	public String deleteUser(@PathVariable("id") Long id){
		userService.delete(id);
		return "redirect:/admin";
	}

	@GetMapping("/user-update/{id}")
	public String updateUserForm(@PathVariable("id") Long id, Model model){
		User user = userService.readUser(id);
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.getAllRoles());
		return "user-update";
	}

	@PostMapping("/user-update")
	public String updateUser(@ModelAttribute("user") User user) {

		userService.update(user);
		return "redirect:/admin";
	}
}
























