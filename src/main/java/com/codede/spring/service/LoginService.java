package com.codede.spring.service;

import com.codede.spring.CustomUserDetail;
import com.codede.spring.entity.Login;
import com.codede.spring.repo.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepo loginRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepo.findByUserName(username);
        if (login == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(login);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        try {
            Login login = loginRepo.findById(id);
            return new CustomUserDetail(login);
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
