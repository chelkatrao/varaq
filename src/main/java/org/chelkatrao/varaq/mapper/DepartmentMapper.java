package org.chelkatrao.varaq.mapper;

import org.chelkatrao.varaq.dto.DepartmentDto;
import org.chelkatrao.varaq.model.Department;
import org.chelkatrao.varaq.repository.DepartmentRepository;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    private UserSession userSession;
    private DepartmentRepository departmentRepository;

    public DepartmentMapper(UserSession userSession, DepartmentRepository departmentRepository) {
        this.userSession = userSession;
        this.departmentRepository = departmentRepository;
    }

    public Department toDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setAccountantFullName(departmentDto.getAccountantFullName());
        department.setAddress(departmentDto.getAddress());
        department.setCode(departmentDto.getCode());
        if (departmentDto.getParentId() != null && departmentRepository.findById(departmentDto.getParentId()).isPresent())
            department.setParent(departmentRepository.findById(departmentDto.getParentId()).get());
        department.setPhone(departmentDto.getPhone());
        department.setShortName(departmentDto.getShortName());
        department.setCreateBy(userSession.getUser().getUsername());
        return department;
    }

    public DepartmentDto toDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .accountantFullName(department.getAccountantFullName())
                .shortName(department.getShortName())
                .phone(department.getPhone())
                .address(department.getAddress())
                .parentId(department.getId())
                .build();
    }

    public List<Department> toDepartment(List<DepartmentDto> companyDtoList) {
        return companyDtoList
                .stream()
                .map(companyDto -> toDepartment(companyDto))
                .collect(Collectors.toList());
    }

    public List<DepartmentDto> toDepartmentDto(List<Department> companyList) {
        return companyList
                .stream()
                .map(company -> toDepartmentDto(company))
                .collect(Collectors.toList());
    }

}
