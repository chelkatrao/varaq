package org.chelkatrao.varaq.controller.auth;

import com.google.common.collect.Sets;
import org.chelkatrao.varaq.dto.auth.UserCreateDto;
import org.chelkatrao.varaq.dto.auth.UserDto;
import org.chelkatrao.varaq.service.DepartmentServiceImpl;
import org.chelkatrao.varaq.service.auth.RoleService;
import org.chelkatrao.varaq.service.auth.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private DepartmentServiceImpl departmentService;

    public UserController(UserService userService, RoleService roleService, DepartmentServiceImpl departmentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.departmentService = departmentService;
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
    public String createUser(@RequestBody UserCreateDto userCreateDto) throws Exception {
        // Hamma userlar bir xil password bilan tizimga kirishadi !!!
        userCreateDto.setPassword("chelkatrao");
        Boolean company = departmentService.findByDepartmentId(userCreateDto.getDepartmentId(), userCreateDto.getDepartmentCode());
        Boolean isExist = departmentService.findByCode(userCreateDto.getDepartmentCode());
        if (isExist && company) {
            userCreateDto.setRoleIds(Sets.newHashSet(roleService.getRoleByName("USER_ROLE").getId()));
            userService.createUser(userCreateDto);
            return "success";
        } else {
            return "error";
        }
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
