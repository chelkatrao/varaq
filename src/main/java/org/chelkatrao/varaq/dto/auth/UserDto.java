package org.chelkatrao.varaq.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chelkatrao.varaq.dto.EmployeeDto;
import org.chelkatrao.varaq.dto.StudentDto;
import org.chelkatrao.varaq.dto.TeacherDto;
import org.chelkatrao.varaq.enums.Status;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private Status status;
    private EmployeeDto employee;
    private StudentDto students;
    private TeacherDto teachers;
    private Set<RoleDto> roles;

    public UserDto(Long id, String username, Status status, EmployeeDto employee, StudentDto students, TeacherDto teachers, Set<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.employee = employee;
        this.students = students;
        this.teachers = teachers;
        this.roles = roles;
    }
}
