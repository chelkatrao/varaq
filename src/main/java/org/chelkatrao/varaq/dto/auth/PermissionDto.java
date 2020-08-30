package org.chelkatrao.varaq.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private String permissionInfo;
    private String permissionName;

    public PermissionDto(Long id,
                         String permissionInfo,
                         String permissionName) {
        this.id = id;
        this.permissionInfo = permissionInfo;
        this.permissionName = permissionName;
    }

}
