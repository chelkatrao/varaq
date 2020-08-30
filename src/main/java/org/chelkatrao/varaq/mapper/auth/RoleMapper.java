package org.chelkatrao.varaq.mapper.auth;

import org.chelkatrao.varaq.dto.auth.PermissionDto;
import org.chelkatrao.varaq.dto.auth.RoleCreateDto;
import org.chelkatrao.varaq.dto.auth.RoleDto;
import org.chelkatrao.varaq.model.auth.Permission;
import org.chelkatrao.varaq.model.auth.Role;
import org.chelkatrao.varaq.repository.auth.PermissionRepository;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private UserSession userSession;

    private PermissionRepository permissionRepository;

    public RoleMapper(UserSession userSession,
                      PermissionRepository permissionRepository) {
        this.userSession = userSession;
        this.permissionRepository = permissionRepository;
    }

    public Role toRole(RoleCreateDto roleCreateDto) {
        return toRole(roleCreateDto, new Role());
    }

    public Role toRole(RoleCreateDto roleCreateDto, Role role) {
        role.setRoleInfo(roleCreateDto.getRoleInfo());
        role.setRoleName(roleCreateDto.getRoleName());
        role.setCreateBy(userSession.getUser().getUsername());
        if (roleCreateDto.getPermissionIds() != null) {
            Set<Permission> permissions = new HashSet<>();
            for (Long permissionId : roleCreateDto.getPermissionIds()) {
                Permission permission = permissionRepository.findById(permissionId).get();
                permissions.add(permission);
            }
            role.setPermissions(permissions);
        } else {
            role.setPermissions(null);
        }
        return role;
    }

    public RoleCreateDto toCreateDto(Role role) {
        RoleCreateDto dto = new RoleCreateDto();
        dto.setId(role.getId());
        dto.setRoleInfo(role.getRoleInfo());
        dto.setRoleName(role.getRoleName());
        Set<Long> permissionIds = new HashSet<>();
        for (Permission permission : role.getPermissions()) {
            permissionIds.add(permission.getId());
        }
        dto.setPermissionIds(permissionIds);
        return dto;
    }

    public Set<RoleDto> listRoleToListRoleDto(Collection<Role> list) {
        Set<RoleDto> listRoleDto = list.stream()
                .map(x -> RoleDto.builder()
                        .id(x.getId())
                        .roleInfo(x.getRoleInfo())
                        .roleName(x.getRoleName())
                        .permission(
                                x.getPermissions().stream()
                                        .map(per ->
                                                PermissionDto.builder()
                                                        .id(per.getId())
                                                        .permissionName(per.getPermissionName())
                                                        .permissionInfo(per.getPermissionInfo())
                                                        .build()
                                        ).collect(Collectors.toSet())
                        ).build()
                ).collect(Collectors.toSet());
        return listRoleDto;
    }

}
