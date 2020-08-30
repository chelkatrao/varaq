package org.chelkatrao.varaq.controller.auth;

import org.chelkatrao.varaq.dto.auth.UserCreateDto;
import org.chelkatrao.varaq.dto.auth.UserDto;
import org.chelkatrao.varaq.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list-full")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN_READ')")
    public List<UserDto> getUserListFull() {
        return userService.getUserListFull();
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN_READ')")
    public List<UserDto> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/new")
    public UserCreateDto createUser(@RequestBody UserCreateDto userCreateDto) throws Exception {
        return userService.createUser(userCreateDto);
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN_WRITE')")
    public Boolean removeUser(@PathVariable("id") Long id) throws Exception {
        return userService.removeUserById(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN_WRITE')")
    public UserCreateDto updateUser(@RequestBody UserCreateDto userUpdateDto,
                                    @PathVariable("id") Long id) throws Throwable {
        return userService.updateUser(userUpdateDto, id);
    }

    @PostMapping("/all-users")
    public List<UserCreateDto> allUsers() {
        return userService.allUsers();
    }
}
