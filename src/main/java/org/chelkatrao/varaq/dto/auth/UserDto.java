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
public class UserDto {
    private Long id;
    private String phoneNumber;
    private Set<RoleDto> roles;

    public UserDto(Long id,
                   String phoneNumber,
                   Set<RoleDto> roles) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
