package org.chelkatrao.varaq.mapper.auth;

import org.chelkatrao.varaq.dto.auth.RoleDto;
import org.chelkatrao.varaq.dto.auth.UserCreateDto;
import org.chelkatrao.varaq.dto.auth.UserDto;
import org.chelkatrao.varaq.mapper.EmployeeMapper;
import org.chelkatrao.varaq.model.auth.Role;
import org.chelkatrao.varaq.model.auth.User;
import org.chelkatrao.varaq.repository.auth.RoleRepository;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserSession userSession;
    private EmployeeMapper employeeMapper;

    public UserMapper(PasswordEncoder passwordEncoder,
                      RoleRepository roleRepository,
                      UserSession userSession,
                      EmployeeMapper employeeMapper) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userSession = userSession;
        this.employeeMapper = employeeMapper;
    }

    public User toUser(UserCreateDto userCreateDto) throws Exception {
        return toUser(userCreateDto, new User());
    }

    public User toUser(UserCreateDto userCreateDto, User user) throws Exception {
        if (userCreateDto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        if (userSession.getUser() != null) {
            user.setCreateBy(userSession.getUser().getUsername());
        } else {
            user.setCreateBy("user");
        }
        user.setUsername(userCreateDto.getUsername());
        if (userCreateDto.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : userCreateDto.getRoles()) {
                Role role = roleRepository.findById(roleId).get();
                roles.add(role);
            }
            user.setRoles(roles);
        } else {
            user.setRoles(null);
        }
        return user;
    }

    public UserCreateDto toCreateDto(User user) {
        UserCreateDto dto = new UserCreateDto();
        dto.setId(user.getId());
        if (user.getEmployee() != null)
            dto.setEmployeeId(user.getEmployee().getId());
        if (user.getTeachers() != null)
            dto.setTeachersId(user.getTeachers().getId());
        if (user.getStudents() != null)
            dto.setStudentsId(user.getStudents().getId());
        dto.setStatus(user.getStatus());
        dto.setUsername(user.getUsername());
        Set<Long> roleIds = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleIds.add(role.getId());
        }
        dto.setRoles(roleIds);
        return dto;
    }

    public List<UserCreateDto> toCreateDto(List<User> users) {
        return users.stream().map(this::toCreateDto).collect(Collectors.toList());
    }

    public List<UserDto> listUserToListUserDto(List<User> list) {

        return list.stream()
                .map(user ->
                        UserDto.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .status(user.getStatus())
                                .roles(user.getRoles().stream()
                                        .map(role -> RoleDto.builder()
                                                .id(role.getId())
                                                .roleName(role.getRoleName())
                                                .roleInfo(role.getRoleInfo())
                                                .build()
                                        ).collect(Collectors.toSet())
                                )
                                .employee(employeeMapper.toEmployeeDto(user.getEmployee()))
                                // TODO shu yerga student bilan teacherlarni qo'shib qo'yish kerak
                                .build())
                .collect(Collectors.toList());
    }
}
