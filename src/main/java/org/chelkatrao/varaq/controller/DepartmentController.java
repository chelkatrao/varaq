package org.chelkatrao.varaq.controller;

import org.chelkatrao.varaq.dto.DepartmentDto;
import org.chelkatrao.varaq.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN_READ','SUPER_ADMIN_WRITE')")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.create(departmentDto);
    }

    @GetMapping("/list")
    public List<DepartmentDto> listDepartment() {
        return departmentService.list();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable("id") Long id) {
        return departmentService.deleteById(id);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.edit(departmentDto);
    }

}
