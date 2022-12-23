package com.codede.spring.service;

import com.codede.spring.DTO.PageDTO;
import com.codede.spring.DTO.PersonDTO;
import com.codede.spring.entity.Person;
import com.codede.spring.repo.PersonRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepo personRepo;

    public void create(PersonDTO personDTO) {
        ModelMapper mapper = new ModelMapper();
//        Person person = mapper.map(personDTO, Person.class);
        Person person = new Person();
//        person.setId(personDTO.getId());
        person.setFullName(personDTO.getFullName());
        person.setAge(personDTO.getAge());
        person.setAddress(personDTO.getAddress());
        person= personRepo.save(person);
    }

    public void update(int id, PersonDTO personDTO) {
        Person person = personRepo.findById(id).orElseThrow(RuntimeException::new);

        person.setFullName(personDTO.getFullName());
        person.setAge(personDTO.getAge());
        person.setAddress(personDTO.getAddress());

        personRepo.save(person);

    }

    public PersonDTO getById(int id) {
        Person person =personRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(person, PersonDTO.class);
    }
    public PageDTO<PersonDTO> getAll() {
        List<PersonDTO> list = new ArrayList<>();
        PageDTO<PersonDTO> pageDTO = new PageDTO<>();
        Person person = new Person();

        for (Person p : personRepo.findAll()
             ) {
            ModelMapper mapper = new ModelMapper();
            PersonDTO personDTO = mapper.map(p, PersonDTO.class);
            list.add(personDTO);
        }
        pageDTO.setContents(list);
        return pageDTO;
    }

    public void delete(int id) {
        personRepo.deleteById(id);
    }

    public PageDTO<PersonDTO> search(String fullName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Person> pageRS = personRepo.findByFullNameLikeIgnoreCase("%"+fullName+"%");
        System.out.println("sdss "+ pageRS +pageable);

        PageDTO<PersonDTO> pageDTO = new PageDTO<>();
//        pageDTO.setTotalPage(pageRS.getTotalPages());
//        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<PersonDTO> personDTOs = new ArrayList<>();
        for (Person person : pageRS) {
            personDTOs.add(new ModelMapper().map(person, PersonDTO.class));
        }

        pageDTO.setContents(personDTOs); // set vao pagedto
        return pageDTO;
    }
}
