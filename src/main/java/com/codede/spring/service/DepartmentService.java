package com.codede.spring.service;

import com.codede.spring.DTO.DepartmentDTO;
import com.codede.spring.DTO.PageDTO;
import com.codede.spring.DTO.PersonDTO;
import com.codede.spring.entity.Department;
import com.codede.spring.entity.Person;
import com.codede.spring.repo.DepartmentRepo;
import com.codede.spring.repo.PersonRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    PersonRepo personRepo;

    @Transactional
    public void create(DepartmentDTO departmentDTO) {
        ModelMapper mapper = new ModelMapper();
        Department department = mapper.map(departmentDTO, Department.class);
        departmentRepo.save(department);
    }

    @Transactional
    public void update(int id, DepartmentDTO departmentDTO) {
        Department department = departmentRepo.findById(id).orElseThrow(RuntimeException::new);

        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        department.setCountry(departmentDTO.getCountry());

        departmentRepo.save(department);
    }

    public DepartmentDTO getById(int id) {
        Department department =departmentRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(department, DepartmentDTO.class);
    }

    @Transactional
    public PageDTO<DepartmentDTO> getAll() {
        List<DepartmentDTO> list = new ArrayList<>();
        PageDTO<DepartmentDTO> pageDTO = new PageDTO<>();
        Department department = new Department();

        for (Department d : departmentRepo.findAll()
        ) {
            ModelMapper mapper = new ModelMapper();
            DepartmentDTO departmentDTO = mapper.map(d, DepartmentDTO.class);
            list.add(departmentDTO);
        }
        pageDTO.setContents(list);
        return pageDTO;
    }

    public void delete(int id) {
        departmentRepo.deleteById(id);
    }


}
