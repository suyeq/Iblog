package com.suye.iblog.service.impl;

import com.suye.iblog.moder.User;
import com.suye.iblog.repository.UserRepository;
import com.suye.iblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{
        /*UserDetailsService*/

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    @Override
    public User getById(Long id) {
        if (id==0){
            return null;
        }
        return userRepository.findOne(id);
    }

    @Transactional
    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name="%"+name+"%";
        Page<User> users=userRepository.findByNameLike(name,pageable);
        return users;
    }

    @Transactional
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }


    public List<User> listUsersByUsernames(Collection<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }
}
