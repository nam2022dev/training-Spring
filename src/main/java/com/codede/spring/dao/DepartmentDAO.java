package com.codede.spring.dao;

import com.codede.spring.DTO.DepartmentDTO;
import com.codede.spring.DTO.PageDTO;
import com.codede.spring.entity.Department;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "departmentDAO")
@Transactional(rollbackFor = Exception.class)

public class DepartmentDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public void persist(final Department department) {
        entityManager.persist(department);
    }
    public Department findById(final int id) {
        return entityManager.find(Department.class, id);
    }
    public void delete(final Department department) {
        entityManager.remove(department);
    }
    public List<Department> findAll() {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM department", Department.class);
            List<Department> departments = query.getResultList();
            return departments;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public PageDTO<Department> searchByName(final String named) {
        try {
            Query query = entityManager.createNativeQuery("select * from department d where d.name like "+named);
            List<Department> departments = query.getResultList();
            PageDTO<Department> pageDTO = new PageDTO<>();
            pageDTO.setContents(departments);
            return pageDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageDTO<>();
        }
    }
}
