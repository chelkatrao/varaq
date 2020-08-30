package org.chelkatrao.varaq.mapper;

import org.chelkatrao.varaq.dto.EmployeeDto;
import org.chelkatrao.varaq.mapper.base.PersonMapper;
import org.chelkatrao.varaq.model.Employee;
import org.chelkatrao.varaq.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {

    private PersonMapper personMapper;
    private DepartmentRepository departmentRepository;

    public EmployeeMapper(PersonMapper personMapper,
                          DepartmentRepository departmentRepository) {
        this.personMapper = personMapper;
        this.departmentRepository = departmentRepository;
    }

    public Employee toEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setStartWorkDate(employeeDto.getStartWorkDate());
        employee.setPassportNumber(employeeDto.getPassportNumber());
        employee.setTaxNumber(employeeDto.getTaxNumber());
        employee.setEmployeeStatus(employeeDto.getEmployeeStatus());
        employee.setDismissDate(employeeDto.getDismissDate());
        employee.setDepartment(departmentRepository.findById(employeeDto.getDepartmentId()).get());

        return (Employee) personMapper.toPerson(employeeDto, employee);
    }

    public EmployeeDto toEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setStartWorkDate(employee.getStartWorkDate());
        employeeDto.setPassportNumber(employee.getPassportNumber());
        employeeDto.setTaxNumber(employee.getTaxNumber());
        employeeDto.setEmployeeStatus(employee.getEmployeeStatus());
        employeeDto.setDismissDate(employee.getDismissDate());
        if (employee.getDepartment() != null)
            employeeDto.setDepartmentId(employee.getDepartment().getId());

        return (EmployeeDto) personMapper.toPersonDto(employeeDto, employee);
    }

    public List<EmployeeDto> toEmployeeDto(List<Employee> employees) {
        List<EmployeeDto> employeeList = new ArrayList<>();
        employees.forEach(employee -> {
            employeeList.add(toEmployeeDto(employee));
        });
        return employeeList;
    }
}
