package com.suye.iblog.controller;

import com.suye.iblog.component.Response;
import com.suye.iblog.moder.User;
import com.suye.iblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户主页空间控制器.
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {


	@Autowired
	private UserServiceImpl userService;

//	@Autowired
//	private UserDetailsService userDetailsService;

	//@Value("${file.server.url}")
	private String fileServerUrl="tg7g7ui";

	/**
	 * 进入编辑信息界面
	 * @param name
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/profile")
//	@PreAuthorize("authentication.name.equals(#username)")//运行该方法前比较是否和当前用户的名字是否相等
	public ModelAndView profile(@PathVariable("username") String name,Model model){
		User user=(User)userService.loadUserByUsername(name);
		model.addAttribute("user",user);
		//model.addAttribute("fileServletUrl",fileServerUrl);
		return new ModelAndView("userspace/profile","userModel",model);
	}

	/**
	 * 保存个人信息的更改
	 * @param username
	 * @param user
	 * @return
	 */
	@PostMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)")
	public String saveProfile(@PathVariable("username") String username,User user) {
		User originalUser = userService.getById(user.getId());
		originalUser.setEmail(user.getEmail());
		originalUser.setName(user.getName());
		// 判断密码是否做了变更
		String rawPassword = originalUser.getPassword();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(user.getPassword());
		boolean isMatch = encoder.matches(rawPassword, encodePasswd);//比较
		if (!isMatch) {
			originalUser.setEncodePassword(user.getPassword());
		}

		userService.saveUpdateUser(originalUser);
		return "redirect:/u/" + username + "/profile";
	}


	/**
	 * 获取编辑头像的界面
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		User  user = (User)userService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/avatar", "userModel", model);
	}


	/**
	 * 保存头像
	 * @param username
	 * @param avatarUrl
	 * @return
	 */
	@PostMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username,@RequestBody Response response) {
		System.out.println(response);
		String avatarUrl=(String) response.getBody();
		User user=(User) userService.loadUserByUsername(username);
		user.setAvatar(avatarUrl);
		userService.saveUpdateUser(user);
		return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
	}




	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username) {
		System.out.println("username" + username);
		return "u";
	}
 
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(@PathVariable("username") String username,
			@RequestParam(value="order",required=false,defaultValue="new") String order,
			@RequestParam(value="category",required=false ) Long category,
			@RequestParam(value="keyword",required=false ) String keyword) {
		
		if (category != null) {
			
			System.out.print("category:" +category );
			System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?category="+category);
			return "/u";
			
		} else if (keyword != null && keyword.isEmpty() == false) {
			
			System.out.print("keyword:" +keyword );
			System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?keyword="+keyword);
			return "/u";
		}  
		
		System.out.print("order:" +order);
		System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?order="+order);
		return "/u";
	}
	
	@GetMapping("/{username}/blogs/{id}")
	public String listBlogsByOrder(@PathVariable("id") Long id) {
		 
		System.out.print("blogId:" + id);
		return "/blog";
	}
	
	
	@GetMapping("/{username}/blogs/edit")
	public String editBlog() {
 
		return "/blogedit";
	}
}
