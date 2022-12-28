package com.codede.spring.dao;

import com.codede.spring.entity.Department;
import com.codede.spring.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "personDAO")
@Transactional(rollbackFor = Exception.class)
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;
    public void persist(final Person person) {
        entityManager.persist(person);
    }

//    public Person findById(final int id) {
//        return entityManager.find(Person.class, id);
//    }

    public Person findById(final int id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM person WHERE id = :id", Person.class);
        query.setParameter("id", id);


        return (Person) query.getSingleResult();
    }

//    public void delete(final Person person) {
//        entityManager.remove(person);
//    }

    public void delete(final Person person) {
        Query query = entityManager.createNativeQuery("DELETE FROM person p WHERE p.id = :id");
        query.setParameter("id", person.getId());
        query.executeUpdate();
    }

//    public void save(final Person person) {
//        entityManager.persist(person);
//    }

    public void save(final Person person) {
        try {
            Query query = entityManager.createNativeQuery("INSERT INTO person(fullname, age, address, departmentid) " +
                    "values(:fullname, :age, :address, :departmentid)");
            query.setParameter("fullname", person.getFullName());
            query.setParameter("age", person.getAge());
            query.setParameter("address", person.getAddress());
            query.setParameter("departmentid", person.getDepartmentId());

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> findAll(final int page){
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM person LIMIT 10 OFFSET (page -1)*10", Person.class);
            query.setParameter("page", page);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Person> searchByName(final String named, int page) {
        try {
            Query query = entityManager.createNativeQuery("select * from person p where p.name like %:named% LIMIT 10 OFFSET (+" + page + "-1)*10", Person.class);
            query.setParameter("named", named);

            List<Person> personList = query.getResultList();
            return personList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Person>();

        }
    }
}
