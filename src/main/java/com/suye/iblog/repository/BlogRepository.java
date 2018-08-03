package com.suye.iblog.repository;

import com.suye.iblog.moder.Blog;
import com.suye.iblog.moder.Catalog;
import com.suye.iblog.moder.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends JpaRepository<Blog,Long>{

    /**
     * 根据用户和标题分页查询
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);


    /**
     * 根据用户名、博客查询博客列表　　（时间逆序）
     * @param title
     * @param user
     * @param tags
     * @param user2
     * @param pageable
     * @return
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title,User user,String tags,User user2,Pageable pageable);


    /**
     * 分类分页查询
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
}
