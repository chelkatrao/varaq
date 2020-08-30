package org.chelkatrao.varaq.controller;

import org.chelkatrao.varaq.dto.EmployeeDto;
import org.chelkatrao.varaq.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN_READ','SUPER_ADMIN_WRITE')")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @GetMapping("/list")
    public List<EmployeeDto> employeeList() {
        return employeeService.list();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") Long id) {
        return employeeService.deleteById(id);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.edit(employeeDto);
    }

}
