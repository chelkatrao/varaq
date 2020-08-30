package org.chelkatrao.varaq.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private String roleInfo;
    private String roleName;
    private Set<PermissionDto> permission;

    public RoleDto(Long id,
                   String roleInfo,
                   String roleName,
                   Set<PermissionDto> permission) {
        this.id = id;
        this.roleInfo = roleInfo;
        this.roleName = roleName;
        this.permission = permission;
    }

}
