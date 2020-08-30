package org.chelkatrao.varaq.security;

import org.chelkatrao.varaq.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public ApplicationUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailDto userDetailDto = userService.getUserByUsername(username);
        return ApplicationUser.builder()
                .password(userDetailDto.getPassword())
                .username(userDetailDto.getUsername())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .grantedAuthorities(userDetailDto.getAuthentications())
                .isEnabled(true)
                .build();
    }

}
