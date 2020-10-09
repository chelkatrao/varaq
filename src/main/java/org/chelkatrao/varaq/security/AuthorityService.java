package org.chelkatrao.varaq.security;

import com.google.common.collect.Sets;
import org.chelkatrao.varaq.enums.UserPermissionEnum;
import org.chelkatrao.varaq.model.Department;
import org.chelkatrao.varaq.model.auth.Permission;
import org.chelkatrao.varaq.model.auth.Role;
import org.chelkatrao.varaq.model.auth.User;
import org.chelkatrao.varaq.repository.DepartmentRepository;
import org.chelkatrao.varaq.repository.auth.PermissionRepository;
import org.chelkatrao.varaq.repository.auth.RoleRepository;
import org.chelkatrao.varaq.repository.auth.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorityService {

    private PermissionRepository permissionRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;

    public AuthorityService(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }


    public void createPermission() {
        Permission permission = permissionRepository.findByPermissionName(UserPermissionEnum.SUPER_ADMIN_READ.name());
        if (permission == null) {
            UserPermissionEnum[] permissionEnums = UserPermissionEnum.values();
            for (UserPermissionEnum permisison : permissionEnums) {
                Permission newPermission = new Permission();
                newPermission.setPermissionName(permisison.name());
                newPermission.setPermissionInfo(permisison.name().toLowerCase()
                        .replace("_", " ").toLowerCase()
                        .replace(":", " ").toLowerCase() + " info"
                        .toLowerCase());
                newPermission.setCreateBy("system");
                permissionRepository.save(newPermission);
            }
        }
    }

    @Transactional
    public void createDepartment() {
        Department isCompanyExist = departmentRepository.findByName("system");
        if (isCompanyExist == null) {
            Department company = new Department();
            company.setName("system");
            company.setCreateBy("system");
            company.setCode("00001");
            departmentRepository.save(company);
        }
    }

    @Transactional
    public void createRole() {
        Role isRoleExist = roleRepository.findByRoleName("SUPER_ADMIN_ROLE");
        if (isRoleExist == null) {
            Role role = new Role();
            role.setRoleName("SUPER_ADMIN_ROLE");
            role.setRoleInfo("super admin role");
            role.setPermissions(Sets.newHashSet(permissionRepository.findAll()));
            role.setCreateBy("system");
            roleRepository.save(role);
        }
    }

    @Transactional
    public void createUserRole() {
        Role isRoleExist = roleRepository.findByRoleName("USER_ROLE");
        if (isRoleExist == null) {
            Role role = new Role();
            role.setRoleName("USER_ROLE");
            role.setRoleInfo("user role");
            List<Permission> userPermission = permissionRepository.findAll().stream().filter(permission -> permission.getPermissionName().startsWith("USER")).collect(Collectors.toList());
            role.setPermissions(Sets.newHashSet(userPermission));
            role.setCreateBy("user");
            roleRepository.save(role);
        }
    }

    @Transactional
    public void createUser() {
        User isUserExist = userRepository.findByUsername("chelkatrao");
        if (isUserExist == null) {
            User user = new User();
            Role isRoleExist = roleRepository.findByRoleName("SUPER_ADMIN_ROLE");
            user.setPassword("$2y$10$OZ.d1M.cVXhKM7CsDQMa0up0uRZOy8ENlsPuaKNb7Gn97nI3C9uCm");
            user.setUsername("chelkatrao");
            user.setRoles(Sets.newHashSet(isRoleExist));
            user.setCreateBy("system");
            userRepository.save(user);
        }
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(Role role) {
        Set<SimpleGrantedAuthority> permission = role.getPermissions()
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermissionName()))
                .collect(Collectors.toSet());
        permission.add(new SimpleGrantedAuthority(role.getRoleName() + "________ROLEEEEEE"));
        return permission;
    }
}
