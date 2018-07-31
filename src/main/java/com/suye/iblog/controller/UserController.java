package com.suye.iblog.controller;

import com.suye.iblog.moder.User;
import com.suye.iblog.reponse.Response;
import com.suye.iblog.service.impl.UserServiceImpl;
import com.suye.iblog.util.DataBaseExcetionHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制器.
 */
@RestController
@RequestMapping("/users")
public class UserController {
 

	@Autowired
	private UserServiceImpl userService;

	/**
	 * 查询所用用户
	 * @return
	 */
	@GetMapping
	public ModelAndView getList(@RequestParam(value="async",required=false) boolean async,
							 @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
							 @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
							 @RequestParam(value="name",required=false,defaultValue="") String name,
							 Model model) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<User> page = userService.listUsersByNameLike(name, pageable);
		List<User> list = page.getContent();	// 当前所在页面数据列表
//		for (User user:list){
//			System.out.println(user);
//		}
		model.addAttribute("page", page);
		model.addAttribute("userList", list);
		return new ModelAndView(async==true?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
	}

	/**
	 * 获取添加用户/新增用户的界面
	 * @return
	 */
	@GetMapping("/add")
	public ModelAndView addUserView(@RequestParam(value = "userId,",required = false,defaultValue = "1") Long id,Model model){
		User user=new User(null,null,null,null);
		model.addAttribute("user",user);
		return new ModelAndView("users/add","userModel",model);
	}

	/**
	 * 获取指定用户的编辑界面
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/edit/{userId}")
	public ModelAndView editUserView(@PathVariable(value = "userId") Long id,Model model){
		User user=userService.getById(id);
		model.addAttribute("user",user);
		return new ModelAndView("users/edit","userModel",model);
	}


	/**
	 * 提交修改/增加的用户表单
	 * @param user
	 * @param model
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response> editForm(User user){
		try{
//			if (user.getId()==null){
//				userService.registerUser(user);
//			}else {
				userService.saveUpdateUser(user);
			//}
		}catch (ConstraintViolationException e){
			System.out.println("\n \n \n"+DataBaseExcetionHandle.getMessage(e));
			return  ResponseEntity.ok().body(new Response(false, DataBaseExcetionHandle.getMessage(e)));
		}
		return  ResponseEntity.ok().body(new Response(true, "提交成功"));
	}


	/**
	 * 删除某个用户
	 * @param id
	 * @return
	 */
	@PostMapping("/{userId}")
	public ResponseEntity<Response> deleteUser(@PathVariable("userId") Long id){
		try {
			userService.removeUser(id);
		} catch (Exception e) {
			return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
		}
		return  ResponseEntity.ok().body( new Response(true, "删除成功"));
	}

}
