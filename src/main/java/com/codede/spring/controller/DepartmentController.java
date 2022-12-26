package com.codede.spring.controller;

import com.codede.spring.DTO.DepartmentDTO;
import com.codede.spring.DTO.PersonDTO;
import com.codede.spring.DTO.ResponseDTO;
import com.codede.spring.entity.Department;
import com.codede.spring.repo.DepartmentRepo;
import com.codede.spring.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentRepo departmentRepo;

    @PostMapping("/new")
    public ResponseDTO<Department> add(@ModelAttribute DepartmentDTO departmentDTO) {
        departmentService.create(departmentDTO);
        Department department = departmentRepo.get();
        return ResponseDTO.<Department>builder().status(200).data(department).build();
    }

    @PostMapping("/edit/{id}")
    public ResponseDTO<DepartmentDTO> edit(@PathVariable("id") int id, @ModelAttribute DepartmentDTO departmentDTO) {
        departmentService.update(id, departmentDTO);
        return ResponseDTO.<DepartmentDTO>builder().status(200).data(departmentDTO).build();
    }
}
