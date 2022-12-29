package com.codede.spring.repo;

import com.codede.spring.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepo extends JpaRepository<Login, Integer> {

    Login findByUserName(String name);

    Login findById(int id);
    Login findById(long id);
}
