package com.suye.iblog.service;

import com.suye.iblog.moder.Authority;
import org.springframework.stereotype.Service;

/**
 * 权限服务接口
 */
@Service
public interface AuthorityService {

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    Authority getAuthorityById(Long id);
}
