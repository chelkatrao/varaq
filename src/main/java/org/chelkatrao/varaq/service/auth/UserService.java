package org.chelkatrao.varaq.service.auth;

import org.chelkatrao.varaq.dto.auth.PermissionDto;
import org.chelkatrao.varaq.dto.auth.RoleDto;
import org.chelkatrao.varaq.dto.auth.UserCreateDto;
import org.chelkatrao.varaq.dto.auth.UserDto;
import org.chelkatrao.varaq.enums.Status;
import org.chelkatrao.varaq.mapper.EmployeeMapper;
import org.chelkatrao.varaq.mapper.auth.UserMapper;
import org.chelkatrao.varaq.model.auth.Role;
import org.chelkatrao.varaq.model.auth.User;
import org.chelkatrao.varaq.repository.auth.UserRepository;
import org.chelkatrao.varaq.security.AuthorityService;
import org.chelkatrao.varaq.security.UserDetailDto;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"userServiceCache"})
public class UserService {

    private UserRepository userRepository;
    private AuthorityService authorityService;
    private UserMapper userMapper;
    private UserSession userSession;
    private JdbcTemplate jdbcTemplate;
    private EmployeeMapper employeeMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthorityService authorityService,
                       UserMapper userMapper,
                       UserSession userSession,
                       JdbcTemplate jdbcTemplate, EmployeeMapper employeeMapper) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.userMapper = userMapper;
        this.userSession = userSession;
        this.jdbcTemplate = jdbcTemplate;
        this.employeeMapper = employeeMapper;
    }

    @CacheEvict
    @Transactional
    public UserCreateDto createUser(UserCreateDto userCreateDto) throws Exception {
        User user = userMapper.toUser(userCreateDto);
        user.setCreateBy(userSession.getUser().getUsername());
        user = userRepository.save(user);
        return userMapper.toCreateDto(user);
    }

    @Cacheable(key = "#root.methodName + '/' + #username")
    public UserDetailDto getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        Set<SimpleGrantedAuthority> authentications = new HashSet<>();

        for (Role role : user.getRoles()) {
            authentications.addAll(authorityService.getGrantedAuthority(role));
        }

        return new UserDetailDto(
                user.getUsername(),
                user.getPassword(),
                authentications
        );
    }

    public User getUserIfExist(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Cacheable(key = "#root.methodName")
    public List<UserDto> getUserListFull() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .employee(employeeMapper.toEmployeeDto(user.getEmployee()))
                        .status(Status.ACTIVE)
                        // TODO Teacher bilan Student ni mapper qilib qo'shib qo'yish kerka employee qo'shilganday
                        .roles(user.getRoles().stream()
                                .map(role -> RoleDto.builder()
                                        .id(role.getId())
                                        .roleInfo(role.getRoleInfo())
                                        .roleName(role.getRoleName())
                                        .permission(
                                                role.getPermissions().stream()
                                                        .map(permission ->
                                                                PermissionDto.builder()
                                                                        .permissionInfo(permission.getPermissionInfo())
                                                                        .permissionName(permission.getPermissionName())
                                                                        .build()).collect(Collectors.toSet())
                                        ).build()).collect(Collectors.toSet())
                        ).build()).collect(Collectors.toList());
    }

    @Cacheable(key = "#root.methodName")
    public List<UserDto> getUserList() {
        List<User> list = userRepository.findAll();
        return userMapper.listUserToListUserDto(list);
    }

    @CacheEvict
    @Transactional
    public Boolean removeUserById(Long id) throws Exception {
        try {
            jdbcTemplate.update(" delete from user_role r where r.user_id =? ", id);
            jdbcTemplate.update(" delete from users u where u.id = ? ", id);
            return true;
        } catch (Exception e) {
            throw new Exception("User not deleted!!!");
        }
    }

    @CacheEvict
    public UserCreateDto updateUser(UserCreateDto userUpdateDto, Long id) throws Exception {
        try {
            User user = userMapper.toUser(userUpdateDto, userRepository.findById(id).get());
            user.setId(id);
            user.setPassword(null);
            User editedUser = userRepository.save(user);
            return userMapper.toCreateDto(editedUser);
        } catch (Exception e) {
            throw new Exception("User not updated something want wrong!!!");
        }
    }

    public List<UserCreateDto> allUsers() {
        return userMapper.toCreateDto(userRepository.findAll());
    }
}
