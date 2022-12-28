package com.codede.spring.dao;

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

//    public Department findById(final int id) {
//        return entityManager.find(Department.class, id);
//    }

    public Department findById(final int id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM department WHERE id = :id", Department.class);
        query.setParameter("id",id);

        return (Department) query.getSingleResult();
    }

//    public void delete(final Department department) {
//        entityManager.remove(department);
//    }

    public void delete(final Department department) {
        Query query = entityManager.createNativeQuery("DELETE FROM department where id = :id");
        query.setParameter("id", department.getId());
        query.executeUpdate();
    }

//    public void save(final Department department) {
//        entityManager.persist(department);
//    }

    public void save(final Department department) {
        try {
            Query query = entityManager.createNativeQuery("INSERT INTO department(name, location, country) VALUES(:name, :location, :country)");
            query.setParameter("name", department.getName());
            query.setParameter("location", department.getLocation());
            query.setParameter("country", department.getCountry());
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Department> findAll(int page) {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM department LIMIT 10 OFFSET (:page -1)*10", Department.class);
            query.setParameter("page", page);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Department> searchByName(final String named, int page) {
        try {
            Query query = entityManager.createNativeQuery("select * from department d where d.name like concat('%', :named, '%') LIMIT 10 OFFSET (:page -1)*10", Department.class);
            query.setParameter("named", named);
            query.setParameter("page", page);

            List<Department> departments = query.getResultList();
            return departments;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();

        }
    }
}
