package com.codede.spring.repo;

import com.codede.spring.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    @Query(value = "SELECT * FROM department ORDER BY id DESC LIMIT 1",
            nativeQuery = true)
    Department get();
}
