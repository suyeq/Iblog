package com.suye.iblog.controller;

import com.suye.iblog.component.Response;
import com.suye.iblog.moder.Blog;
import com.suye.iblog.moder.Catalog;
import com.suye.iblog.moder.User;
import com.suye.iblog.moder.Vote;
import com.suye.iblog.service.impl.BlogServiceImpl;
import com.suye.iblog.service.impl.UserServiceImpl;
import com.suye.iblog.util.DataBaseExcetionHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 用户主页空间控制器.
 */
@RestController
@RequestMapping("/u")
public class UserspaceController {


	@Autowired
	private UserServiceImpl userService;


	@Autowired
	private BlogServiceImpl blogService;

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
	//@PreAuthorize("authentication.name.equals(#username)")//运行该方法前比较是否和当前用户的名字是否相等
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
	//@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		User  user = (User)userService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/avatar", "userModel", model);
	}


	/**
	 * 保存头像
	 * @param username
	 * @param response
	 * @return 头像地址
	 */
	@PostMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public String saveAvatar(@PathVariable("username") String username,@RequestBody Response response) {
		String avatarUrl=(String) response.getBody();
		User user=(User) userService.loadUserByUsername(username);
		user.setAvatar(avatarUrl);
		userService.saveUpdateUser(user);
		return avatarUrl;
	}


	/**
	 * 进入个人空间
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}")
	public ModelAndView userSpace(@PathVariable("username") String username,Model model) {
		User user=(User) userService.loadUserByUsername(username);
		model.addAttribute("user",user);
		return new ModelAndView("/u/"+username+"/blogs",null,model);
	}

	/**
	 * 展示某人　博客
	 * @param username
	 * @param order
	 * @param catalogId
	 * @param keyword
	 * @param async
	 * @param pageIndex
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(@PathVariable("username") String username,
								   @RequestParam(value="order",required=false,defaultValue="new") String order,
								   @RequestParam(value="catalog",required=false ) Long catalogId,
								   @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
								   @RequestParam(value="async",required=false) boolean async,
								   @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
								   @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
								   Model model) {

		User  user = (User)userService.loadUserByUsername(username);
		Page<Blog> page = null;
		if (catalogId != null && catalogId > 0) { // 分类查询
			//Catalog catalog = catalogService.getCatalogById(catalogId);
			//Pageable pageable = new PageRequest(pageIndex, pageSize);
			//page = blogService.listBlogsByCatalog(catalog, pageable);
			//order = "";
		} else if (order.equals("hot")) { // 最热查询
			Sort sort = new Sort(Sort.Direction.DESC,"readSize","commentSize","voteSize");
			Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
			page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);
		} else if (order.equals("new")) { // 最新查询
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByTitleVote(user, keyword, pageable);
		}
		List<Blog> list = page.getContent();	// 当前所在页面数据列表
		model.addAttribute("user", user);
		model.addAttribute("order", order);
		model.addAttribute("catalogId", catalogId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		return (async==true?"/userspace/u :: #mainContainerRepleace":"/userspace/u");
	}



	/**
	 * 获取博客展示界面
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/{id}")
	public String getBlogById(@PathVariable("username") String username,@PathVariable("id") Long id, Model model) {
		User principal = null;
		Blog blog = blogService.getBlogById(id);

		// 每次读取，简单的可以认为阅读量增加1次
		blogService.readingIncrease(id);

		// 判断操作用户是否是博客的所有者
		boolean isBlogOwner = false;
		if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal !=null && username.equals(principal.getUsername())) {
				isBlogOwner = true;
			}
		}

		// 判断操作用户的点赞情况
		List<Vote> votes = blog.getVotes();
		Vote currentVote = null; // 当前用户的点赞情况

		if (principal !=null) {
			for (Vote vote : votes) {
				vote.getUser().getUsername().equals(principal.getUsername());
				currentVote = vote;
				break;
			}
		}

		model.addAttribute("isBlogOwner", isBlogOwner);
		model.addAttribute("blogModel",blog);
		model.addAttribute("currentVote",currentVote);

		return "/userspace/blog";
	}


	/**
	 * 删除博客
	 * @param id
	 * @param model
	 * @return
	 */
	@DeleteMapping("/{username}/blogs/{id}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username,@PathVariable("id") Long id) {

		try {
			blogService.removeBlog(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/u/" + username + "/blogs";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}

	/**
	 * 获取新增博客的界面
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/edit")
	public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
		User user = (User)userService.loadUserByUsername(username);
		//List<Catalog> catalogs = catalogService.listCatalogs(user);

		model.addAttribute("blog", new Blog(null, null, null));
		//model.addAttribute("catalogs", catalogs);
		return new ModelAndView("/userspace/blogedit", "blogModel", model);
	}

	/**
	 * 获取编辑博客的界面
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/edit/{id}")
	public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		// 获取用户分类列表
		User user = (User)userService.loadUserByUsername(username);
		//List<Catalog> catalogs = catalogService.listCatalogs(user);

		model.addAttribute("blog", blogService.getBlogById(id));
		//model.addAttribute("catalogs", catalogs);
		return new ModelAndView("/userspace/blogedit", "blogModel", model);
	}

	/**
	 * 保存博客
	 * @param username
	 * @param blog
	 * @return
	 */
	@PostMapping("/{username}/blogs/edit")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
		// 对 Catalog 进行空处理
		if (blog.getCatalog().getId() == null) {
			return ResponseEntity.ok().body(new Response(false,"未选择分类"));
		}
		try {

			// 判断是修改还是新增

			if (blog.getId()!=null) {
				Blog orignalBlog = blogService.getBlogById(blog.getId());
				orignalBlog.setTitle(blog.getTitle());
				orignalBlog.setContent(blog.getContent());
				orignalBlog.setSummary(blog.getSummary());
				orignalBlog.setCatalog(blog.getCatalog());
				orignalBlog.setTags(blog.getTags());
				blogService.saveBlog(orignalBlog);
			} else {
				User user = (User)userService.loadUserByUsername(username);
				blog.setUser(user);
				blogService.saveBlog(blog);
			}

		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, DataBaseExcetionHandle.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
}
