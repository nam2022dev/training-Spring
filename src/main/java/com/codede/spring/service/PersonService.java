package com.codede.spring.service;

import com.codede.spring.DTO.PageDTO;
import com.codede.spring.DTO.PersonDTO;
import com.codede.spring.dao.PersonDAO;
import com.codede.spring.entity.Person;
import com.codede.spring.repo.PersonRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    PersonDAO personDAO;

    @Transactional
    public void create(PersonDTO personDTO) {
        ModelMapper mapper = new ModelMapper();
        Person person = mapper.map(personDTO, Person.class);
        personDAO.save(person);
    }

    @Transactional
    public PersonDTO update(int id, PersonDTO personDTO) {
        try {
            Person person = personDAO.findById(id);
            person.setFullName(personDTO.getFullName());
            person.setAge(personDTO.getAge());
            person.setAddress(personDTO.getAddress());
            person.setDepartmentId(personDTO.getDepartmentId());

            personDAO.save(person);
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return personDTO;
    }

    @Transactional
    public PersonDTO getById(int id) {
        Person person = personDAO.findById(id);
        return new ModelMapper().map(person, PersonDTO.class);
    }

    @Transactional
    public PageDTO<PersonDTO> getAll(int page) {
        List<PersonDTO> list = new ArrayList<>();
        PageDTO<PersonDTO> pageDTO = new PageDTO<>();
        Person person = new Person();

        for (Person p : personDAO.findAll(page)
        ) {
            ModelMapper mapper = new ModelMapper();
            PersonDTO personDTO = mapper.map(p, PersonDTO.class);
            list.add(personDTO);
        }
        pageDTO.setContents(list);
        return pageDTO;
    }

    @Transactional
    public void delete(int id) {
        Person person = personDAO.findById(id);
        personDAO.delete(person);
    }

    @Transactional
    public List<Person> searchByName(String name, int page) {
        return personDAO.searchByName(name, page);

    }
}
