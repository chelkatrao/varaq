package org.chelkatrao.varaq.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class RoleCreateDto {

    private Long id;
    private String roleInfo;
    private String roleName;
    private List<PermissionDto> permissionDtoList;

    public RoleCreateDto(Long id,
                         String roleInfo,
                         String roleName,
                         List<PermissionDto> permissionDtoList) {
        this.id = id;
        this.roleInfo = roleInfo;
        this.roleName = roleName;
        this.permissionDtoList = permissionDtoList;
    }

}
