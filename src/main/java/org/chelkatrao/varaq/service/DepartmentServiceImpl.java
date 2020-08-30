package org.chelkatrao.varaq.service;

import org.chelkatrao.varaq.dto.DepartmentDto;
import org.chelkatrao.varaq.mapper.DepartmentMapper;
import org.chelkatrao.varaq.model.Department;
import org.chelkatrao.varaq.repository.DepartmentRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"departmentServiceCache"})
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    @CacheEvict
    public DepartmentDto create(DepartmentDto departmentDto) {
        return departmentMapper.toDepartmentDto(
                departmentRepository.save(departmentMapper.toDepartment(departmentDto))
        );
    }

    @Cacheable(key = "#root.method")
    public List<DepartmentDto> list() {
        return departmentMapper.toDepartmentDto(departmentRepository.findAll());
    }

    @CacheEvict
    public ResponseEntity<?> deleteById(Long id) {
        departmentRepository.deleteById(id);
        return ResponseEntity.ok("success deleted");
    }

    @CacheEvict
    public ResponseEntity<?> edit(DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getId()).get();
        department.setName(departmentDto.getName());
        department.setAccountantFullName(departmentDto.getAccountantFullName());
        department.setAddress(departmentDto.getAddress());
        department.setCode(departmentDto.getCode());
        if (departmentDto.getParentId() != null && departmentRepository.findById(departmentDto.getParentId()).isPresent()) {
            department.setParent(departmentRepository.findById(departmentDto.getParentId()).get());
        } else department.setParent(null);
        department.setPhone(departmentDto.getPhone());
        department.setShortName(departmentDto.getShortName());
        departmentRepository.save(department);

        return new ResponseEntity<>(departmentMapper.toDepartmentDto(department), HttpStatus.OK);
    }

    @CacheEvict
    public Boolean findByCode(String departmentCode) {
        return departmentRepository.findByCode(departmentCode) != null;
    }

    public Boolean findByDepartmentId(Long departmentId, String departmentCode) {
        return departmentRepository.findById(departmentId).get().getCode().equals(departmentCode);
    }
}
