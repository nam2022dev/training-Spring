package com.codede.spring.controller;

import com.codede.spring.DTO.PageDTO;
import com.codede.spring.DTO.PersonDTO;
import com.codede.spring.DTO.ResponseDTO;
import com.codede.spring.entity.Person;
import com.codede.spring.repo.PersonRepo;
import com.codede.spring.service.PersonService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all")
    public ResponseDTO<PageDTO<PersonDTO>> getAll() {
        return ResponseDTO.<PageDTO<PersonDTO>>builder().status(200).data(personService.getAll()).build();
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

    @GetMapping("/search")
    public ResponseDTO<PageDTO<PersonDTO>> search(@RequestParam(name = "name", required = false) String name,
                                                  @RequestParam(name = "size", required = false) Integer size,
                                                  @RequestParam(name = "page", required = false) Integer page) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
//        name = name == null ? "%%" : name;

        PageDTO<PersonDTO> pageRS = personService.search(name, page, size);
        System.out.println(pageRS);

        return ResponseDTO.<PageDTO<PersonDTO>>builder().status(200).data(pageRS).build();

    }
}
