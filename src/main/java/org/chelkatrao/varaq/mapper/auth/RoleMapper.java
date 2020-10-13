package org.chelkatrao.varaq.mapper.auth;

import org.chelkatrao.varaq.dto.auth.PermissionDto;
import org.chelkatrao.varaq.dto.auth.RoleCreateDto;
import org.chelkatrao.varaq.dto.auth.RoleDto;
import org.chelkatrao.varaq.model.auth.Permission;
import org.chelkatrao.varaq.model.auth.Role;
import org.chelkatrao.varaq.repository.auth.PermissionRepository;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private UserSession userSession;
    private PermissionMapper permissionMapper;

    public RoleMapper(UserSession userSession,
                      PermissionMapper permissionMapper) {
        this.userSession = userSession;
        this.permissionMapper = permissionMapper;
    }

    public Role toRole(RoleCreateDto roleCreateDto) {
        return toRole(roleCreateDto, new Role());
    }

    public Role toRole(RoleCreateDto roleCreateDto, Role role) {
        role.setRoleInfo(roleCreateDto.getRoleInfo());
        role.setRoleName(roleCreateDto.getRoleName());
        role.setCreateBy(userSession.getUser().getUsername());
        if (roleCreateDto.getPermissionDtoList() != null) {
            Set<Permission> permissions = new HashSet<>();
            for (PermissionDto permissionDto : roleCreateDto.getPermissionDtoList()) {
                Permission permission = permissionMapper.toPermission(permissionDto);
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
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for (Permission permission : role.getPermissions()) {
            permissionDtoList.add(permissionMapper.toDto(permission));
        }
        dto.setPermissionDtoList(permissionDtoList);
        return dto;
    }

    public Set<RoleDto> listRoleToListRoleDto(Collection<Role> list) {
        return list.stream()
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
    }

}
