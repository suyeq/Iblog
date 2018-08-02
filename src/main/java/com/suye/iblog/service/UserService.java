package com.suye.iblog.service;

import com.suye.iblog.moder.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
/**
 * 用户服务借口
 */
public interface UserService {

    /**
     * 跟新保存一个新用户
     * @param user
     * @return
     */
    User saveUpdateUser(User user);

    /**
     * 注册用户
     * @param user
     * @return
     */
    User registerUser(User user);

    /**
     * 删除一个用户
     * @param id
     */
    void removeUser(Long id);

    /**
     * 查询一个用户
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 根据姓名模糊分页查询
     * @param name
     * @param pageable
     * @return
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);
}
