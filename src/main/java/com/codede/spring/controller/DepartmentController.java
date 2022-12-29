package com.codede.spring.controller;

import com.codede.spring.DTO.DepartmentDTO;
import com.codede.spring.DTO.ResponseDTO;
import com.codede.spring.entity.Department;
import com.codede.spring.repo.DepartmentRepo;
import com.codede.spring.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") int id) {
        departmentService.deleteById(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}")
    public ResponseDTO<DepartmentDTO> getById(@PathVariable("id") int id) {

        return ResponseDTO.<DepartmentDTO>builder().status(200).data(departmentService.getById(id)).build();
    }

    @GetMapping("/get-all/{page}")
    public ResponseDTO<List<DepartmentDTO>> getAll(@PathVariable("page") int page) {
        return ResponseDTO.<List<DepartmentDTO>>builder().status(200).data(departmentService.getAll(page)).build();
    }

    @GetMapping("/search/{page}")
    public ResponseDTO<List<Department>> searchByName(@RequestParam("name") String name, @PathVariable("page") int page) {
        return ResponseDTO.<List<Department>>builder()
                .status(200)
                .data(departmentService.searchByName(name, page))
                .build();
    }
}
