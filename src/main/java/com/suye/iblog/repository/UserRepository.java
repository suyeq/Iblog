package com.suye.iblog.repository;

import com.suye.iblog.moder.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户仓库
 */
@Repository
public interface UserRepository  extends JpaRepository<User,Long>{

    /**
     * 根据用户姓名分页查询
     * @param name
     * @return　
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据用户姓名来查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
