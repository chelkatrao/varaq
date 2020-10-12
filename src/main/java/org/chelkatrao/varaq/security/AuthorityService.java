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

    /*
    * Create permissions
    * */

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

    /*
    * Create department
    * */

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

    /*
    *  Create role admin
    * */

    @Transactional
    public void createRoleAdmin() {
        Role isRoleExist = roleRepository.findByRoleName("ROLE_ADMIN");
        if (isRoleExist == null) {
            Role role = new Role();
            role.setRoleName("ROLE_ADMIN");
            role.setRoleInfo("role admin");
            role.setPermissions(Sets.newHashSet(permissionRepository.findAll()));
            role.setCreateBy("system");
            roleRepository.save(role);
        }
    }

    /*
    *  Crate role user
    * */

    @Transactional
    public void createRoleUser() {
        Role isRoleExist = roleRepository.findByRoleName("ROLE_USER");
        if (isRoleExist == null) {
            Role role = new Role();
            role.setRoleName("ROLE_USER");
            role.setRoleInfo("role_user");
            List<Permission> userPermission = permissionRepository.findAll().stream().filter(permission -> permission.getPermissionName().startsWith("USER")).collect(Collectors.toList());
            role.setPermissions(Sets.newHashSet(userPermission));
            role.setCreateBy("user");
            roleRepository.save(role);
        }
    }

    /*
     *  Crate role teacher
     * */

    @Transactional
    public void createRoleTeacher() {
        Role isRoleExist = roleRepository.findByRoleName("ROLE_TEACHER");
        if (isRoleExist == null) {
            Role role = new Role();
            role.setRoleName("ROLE_TEACHER");
            role.setRoleInfo("role_teacher");
            List<Permission> userPermission = permissionRepository.findAll().stream().filter(permission -> permission.getPermissionName().startsWith("TEACHER")).collect(Collectors.toList());
            role.setPermissions(Sets.newHashSet(userPermission));
            role.setCreateBy("user");
            roleRepository.save(role);
        }
    }

    /*
    *  Crate user
    * */

    @Transactional
    public void createUser() {
        User isUserExist = userRepository.findByUsername("chelkatrao");
        if (isUserExist == null) {
            User user = new User();
            Role isRoleExist = roleRepository.findByRoleName("ROLE_ADMIN");
            user.setPassword("$2y$10$OZ.d1M.cVXhKM7CsDQMa0up0uRZOy8ENlsPuaKNb7Gn97nI3C9uCm");
            user.setUsername("chelkatrao");
            user.setRoles(Sets.newHashSet(isRoleExist));
            user.setCreateBy("system");
            userRepository.save(user);
        }
    }

    /*
    *  Get permissions
    * */

    public Set<SimpleGrantedAuthority> getGrantedAuthority(Role role) {
        Set<SimpleGrantedAuthority> permission = role.getPermissions()
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermissionName()))
                .collect(Collectors.toSet());
        permission.add(new SimpleGrantedAuthority(role.getRoleName()));
        return permission;
    }
}
