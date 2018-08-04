package com.suye.iblog.controller;

import com.suye.iblog.moder.Authority;
import com.suye.iblog.moder.User;
import com.suye.iblog.service.impl.AuthorityServiceImle;
import com.suye.iblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页控制器.
 */
@Controller
public class MainController {

	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthorityServiceImle authorityService;

	@GetMapping("/")
	public String root() {
		return "redirect:/blogs";
	}
	
	@GetMapping("/index")
	public String index() {
		return "redirect:/blogs";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(User user){
		List<Authority> authorities=new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		userService.registerUser(user);
		return "redirect:/login";
	}


	@GetMapping("/search")
	public String search() {
		return "search";
	}
}
