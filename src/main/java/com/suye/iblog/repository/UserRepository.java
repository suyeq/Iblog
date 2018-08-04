package com.suye.iblog.repository;

import com.suye.iblog.moder.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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

    /**
     * 根据名称列表查询
     * @param usernames
     * @return
     */
    List<User> findByUsernameIn(Collection<String> usernames);
}
