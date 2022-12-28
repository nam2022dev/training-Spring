package com.codede.spring.service;

import com.codede.spring.DTO.DepartmentDTO;
import com.codede.spring.DTO.PageDTO;
import com.codede.spring.dao.DepartmentDAO;
import com.codede.spring.entity.Department;
import com.codede.spring.repo.DepartmentRepo;
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
public class DepartmentService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    DepartmentDAO departmentDAO;

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
        Department department = departmentDAO.findById(id);
        return new ModelMapper().map(department, DepartmentDTO.class);
    }

    @Transactional
    public PageDTO<DepartmentDTO> getAll() {
        List<DepartmentDTO> list = new ArrayList<>();
        PageDTO<DepartmentDTO> pageDTO = new PageDTO<>();

        for (Department d : departmentDAO.findAll()
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

    @Transactional
    public PageDTO<Department> searchByName(String name) {
        return departmentDAO.searchByName(name);

    }
}
