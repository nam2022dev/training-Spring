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
        departmentDAO.save(department);
    }

    @Transactional
    public void update(int id, DepartmentDTO departmentDTO) {
        Department department = departmentDAO.findById(departmentDTO.getId());

        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        department.setCountry(departmentDTO.getCountry());

        departmentDAO.save(department);
    }

    @Transactional
    public DepartmentDTO getById(int id) {
        Department department = departmentDAO.findById(id);
        return new ModelMapper().map(department, DepartmentDTO.class);
    }

    @Transactional
    public List<DepartmentDTO> getAll(int page) {
        List<DepartmentDTO> list = new ArrayList<>();

        for (Department d : departmentDAO.findAll(page)
        ) {
            ModelMapper mapper = new ModelMapper();
            DepartmentDTO departmentDTO = mapper.map(d, DepartmentDTO.class);
            list.add(departmentDTO);
        }
        return list;
    }

    @Transactional
    public void deleteById(int id) {
        Department department1 = departmentDAO.findById(id);
        departmentDAO.delete(department1);
    }

    @Transactional
    public List<Department> searchByName(String name, int page) {
        return departmentDAO.searchByName(name, page);

    }
}
