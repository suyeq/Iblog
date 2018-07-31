package com.suye.iblog.service.impl;

import com.suye.iblog.moder.Authority;
import com.suye.iblog.repository.AuthorityRepository;
import com.suye.iblog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImle  implements AuthorityService{

    @Autowired
    private AuthorityRepository authorityRepository;


    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }
}
