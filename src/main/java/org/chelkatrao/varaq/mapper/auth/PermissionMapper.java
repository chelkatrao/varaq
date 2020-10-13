package org.chelkatrao.varaq.mapper.auth;

import org.chelkatrao.varaq.dto.auth.PermissionDto;
import org.chelkatrao.varaq.model.auth.Permission;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    public PermissionDto toDto(Permission permission) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setPermissionInfo(permission.getPermissionInfo());
        permissionDto.setPermissionName(permission.getPermissionName());
        return permissionDto;
    }

    public Permission toPermission(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setPermissionInfo(permissionDto.getPermissionInfo());
        permission.setPermissionName(permissionDto.getPermissionName());
        return permission;
    }

    public Set<PermissionDto> listPermissionToListPermissionDto(Collection<Permission> list) {
        return list.stream().map(this::toDto).collect(Collectors.toSet());
    }

}
