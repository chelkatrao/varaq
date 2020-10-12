package org.chelkatrao.varaq.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chelkatrao.varaq.dto.EmployeeDto;
import org.chelkatrao.varaq.enums.Status;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private Long id;
    private String username;
    private Status status;
    private EmployeeDto employeeDto;
//    private Long studentsId;
//    private Long teachersId;
    private Long departmentId;
    private String password;
    private Set<Long> roles;

}
