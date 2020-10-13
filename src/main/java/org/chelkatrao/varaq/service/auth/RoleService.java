package org.chelkatrao.varaq.service.auth;

import lombok.SneakyThrows;
import org.chelkatrao.varaq.dto.auth.RoleCreateDto;
import org.chelkatrao.varaq.dto.auth.RoleDto;
import org.chelkatrao.varaq.mapper.auth.PermissionMapper;
import org.chelkatrao.varaq.mapper.auth.RoleMapper;
import org.chelkatrao.varaq.model.auth.Role;
import org.chelkatrao.varaq.repository.auth.RoleRepository;
import org.chelkatrao.varaq.repository.auth.UserRepository;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"roleServiceCache"})
public class RoleService {

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private RoleMapper roleMapper;

    private PermissionMapper permissionMapper;

    private final JdbcTemplate jdbcTemplate;

    private final UserSession userSession;

    public RoleService(RoleRepository roleRepository,
                       UserRepository userRepository,
                       RoleMapper roleMapper,
                       PermissionMapper permissionMapper,
                       JdbcTemplate jdbcTemplate,
                       UserSession userSession) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.userSession = userSession;
    }

    @CacheEvict
    @Transactional
    public RoleCreateDto createRole(RoleCreateDto roleCreateDto) {
        Role role = roleMapper.toRole(roleCreateDto);
        role.setRoleName(role.getRoleName().toUpperCase());
        if (!role.getRoleName().endsWith("_ROLE"))
            role.setRoleName(role.getRoleName() + "_ROLE");
        role = roleRepository.save(role);
        return roleMapper.toCreateDto(role);
    }

    @Cacheable(key = "#root.methodName")
    public Set<RoleDto> getListRoles() {
        List<Role> listRole = roleRepository.findAll();
        return roleMapper.listRoleToListRoleDto(listRole);
    }

    @CacheEvict
    @SneakyThrows
    public Boolean removeRoleById(Long id) {
        try {
            jdbcTemplate.update(" delete from u_roles_permissions r where r.role_id =? ", id);
            jdbcTemplate.update(" delete from role r where r.id = ? ", id);
            return true;
        } catch (Exception e) {
            throw new IllegalStateException("User not deleted!!!");
        }
    }

    @Cacheable(key = "#root.methodName")
    public Set<RoleDto> getRoleByUserId(Long userId) {
        Set<Role> listRole = userRepository.findById(userId).get().getRoles();
        return roleMapper.listRoleToListRoleDto(listRole);
    }

    @Cacheable(key = "#root.methodName")
    public RoleCreateDto updateRole(RoleCreateDto roleCreateDto, Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setRoleName(roleCreateDto.getRoleName());
            role.setRoleInfo(roleCreateDto.getRoleInfo());
            role.setPermissions(roleCreateDto.getPermissionDtoList()
                    .stream()
                    .map(permissionDto -> permissionMapper.toPermission(permissionDto))
                    .collect(Collectors.toSet()));
            role.setCreateBy(userSession.getUser().getUsername());
            return roleMapper.toCreateDto(roleRepository.save(role));
        } else {
            throw new IllegalArgumentException("error while updating");
        }
    }

    @Cacheable(key = "#root.methodName")
    public RoleCreateDto getRoleByName(String roleName) {
        return roleMapper.toCreateDto(roleRepository.findByRoleName(roleName));
    }

    public RoleCreateDto getRoleByRoleId(Long roleId) {
        return roleMapper.toCreateDto(roleRepository.findById(roleId).get());
    }
}
