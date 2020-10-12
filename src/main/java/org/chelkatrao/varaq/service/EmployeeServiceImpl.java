package org.chelkatrao.varaq.service;

import org.chelkatrao.varaq.dto.EmployeeDto;
import org.chelkatrao.varaq.mapper.EmployeeMapper;
import org.chelkatrao.varaq.mapper.base.PersonMapper;
import org.chelkatrao.varaq.model.Employee;
import org.chelkatrao.varaq.repository.DepartmentRepository;
import org.chelkatrao.varaq.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private DepartmentRepository departmentRepository;
    private PersonMapper personMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, DepartmentRepository departmentRepository, PersonMapper personMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.departmentRepository = departmentRepository;
        this.personMapper = personMapper;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        return employeeMapper.toEmployeeDto(employeeRepository.save(employeeMapper.toEmployee(employeeDto)));
    }

    @Override
    public List<EmployeeDto> list() {
        return employeeMapper.toEmployeeDto(employeeRepository.findAll());
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("success deleted");
    }

    @Override
    public ResponseEntity<?> edit(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId()).get();
//        if (employeeDto.getDepartmentId() != null && departmentRepository.findById(employeeDto.getDepartmentId()).isPresent())
//            employee.setDepartment(departmentRepository.findById(employeeDto.getDepartmentId()).get());
        employee.setDismissDate(employeeDto.getDismissDate());
        employee.setStartWorkDate(employeeDto.getDismissDate());
        employee.setPassportNumber(employeeDto.getPassportNumber());
        employee.setTaxNumber(employeeDto.getTaxNumber());
        employee.setEmployeeStatus(employeeDto.getEmployeeStatus());
        employee.setDismissDate(employeeDto.getDismissDate());
        personMapper.toPerson(employeeDto, employee);
        return new ResponseEntity<>(employeeMapper.toEmployeeDto(employee), HttpStatus.OK);
    }

}
