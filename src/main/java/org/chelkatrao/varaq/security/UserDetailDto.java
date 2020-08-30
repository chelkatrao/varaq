package org.chelkatrao.varaq.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto {

    private String username;
    private String password;
    private Set<SimpleGrantedAuthority> authentications;

    public UserDetailDto(String username,
                         String password,
                         Set<SimpleGrantedAuthority> authentications) {
        this.username = username;
        this.password = password;
        this.authentications = authentications;
    }

}
