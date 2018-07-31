package com.suye.iblog.repository;

import com.suye.iblog.moder.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 权限的仓库
 */
@Repository
public interface AuthorityRepository  extends JpaRepository<Authority,Long>{
}
