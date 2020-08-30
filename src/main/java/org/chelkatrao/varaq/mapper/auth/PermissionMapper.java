package org.chelkatrao.varaq.mapper.auth;

import org.chelkatrao.varaq.dto.auth.PermissionDto;
import org.chelkatrao.varaq.model.auth.Permission;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {
    public Set<PermissionDto> listPermissionToListPermissionDto(Collection<Permission> list) {
        Set<PermissionDto> listPermissionDto = list.stream()
                .map(p -> PermissionDto.builder()
                        .id(p.getId())
                        .permissionInfo(p.getPermissionInfo())
                        .permissionName(p.getPermissionName())
                        .build()
                ).collect(Collectors.toSet());
        return listPermissionDto;
    }
}
