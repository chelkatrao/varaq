package org.chelkatrao.varaq.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chelkatrao.varaq.dto.EmployeeDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private Long id;
    private String username;
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    private String fullName;
    private String age;
    private String phoneNumber;
    private Set<Long> roleIds;
    private EmployeeDto employeeDto;
    @JsonIgnore
    private String password;

}
