package com.suye.iblog.repository;

import com.suye.iblog.moder.Catalog;
import com.suye.iblog.moder.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Long>{
    /**
     * 根据用户查询
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户查询
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user,String name);
}

