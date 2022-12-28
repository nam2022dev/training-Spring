package com.codede.spring.controller;

import com.codede.spring.DTO.PageDTO;
import com.codede.spring.DTO.PersonDTO;
import com.codede.spring.DTO.ResponseDTO;
import com.codede.spring.entity.Person;
import com.codede.spring.repo.PersonRepo;
import com.codede.spring.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonRepo personRepo;
    @Autowired
    PersonService personService;

    @PostMapping("/new")
    public ResponseDTO<Person> add(@ModelAttribute PersonDTO personDTO) {
        personService.create(personDTO);
        Person person = personRepo.get();
        return ResponseDTO.<Person>builder().status(200).data(person).build();
    }

    @GetMapping("/get/{id}")
    public ResponseDTO<PersonDTO> get(@PathVariable("id") int id) {
        PersonDTO personDTO = personService.getById(id);
        return ResponseDTO.<PersonDTO>builder().status(200).data(personDTO).build();
    }

    @GetMapping("/get-all/{page}")
    @Transactional
    public ResponseDTO<PageDTO<PersonDTO>> getAll(@PathVariable("page") int page) {
        return ResponseDTO.<PageDTO<PersonDTO>>builder().status(200).data(personService.getAll(page)).build();
    }

    @PostMapping("/edit/{id}")
    public ResponseDTO<PersonDTO> edit(@PathVariable("id") int id, @ModelAttribute PersonDTO personDTO) {
        personService.update(id, personDTO);
        return ResponseDTO.<PersonDTO>builder().status(200).data(personDTO).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") int id) {
        personService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/search/{page}")
    public ResponseDTO<List<Person>> searchByName(@RequestParam("name") String name, @PathVariable("page") int page) {
        return ResponseDTO.<List<Person>>builder()
                .status(200)
                .data(personService.searchByName(name, page))
                .build();
    }
}
