package org.chelkatrao.varaq.service.auth;

import org.chelkatrao.varaq.dto.auth.PermissionDto;
import org.chelkatrao.varaq.mapper.auth.PermissionMapper;
import org.chelkatrao.varaq.model.auth.Permission;
import org.chelkatrao.varaq.repository.auth.PermissionRepository;
import org.chelkatrao.varaq.repository.auth.RoleRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"userServiceCache"})
public class PermissionService {

    private PermissionRepository permissionRepository;

    private RoleRepository roleRepository;

    private PermissionMapper permissionMapper;

    public PermissionService(PermissionRepository permissionRepository,
                             RoleRepository roleRepository,
                             PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.permissionMapper = permissionMapper;
    }

    @Cacheable(key = "#root.methodName + '/' + #username")
    public List<PermissionDto> getAllPerms() {
        List<Permission> listPermission = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = listPermission.stream()
                .map(x -> PermissionDto.builder()
                        .id(x.getId())
                        .permissionName(x.getPermissionName())
                        .permissionInfo(x.getPermissionInfo())
                        .build())
                .collect(Collectors.toList());
        return permissionDtoList;
    }

    @Cacheable(key = "#root.methodName")
    public Set<PermissionDto> getPermissionByRoleId(Long userId) {
        Set<Permission> listPermission = roleRepository.findById(userId).get().getPermissions();
        return permissionMapper.listPermissionToListPermissionDto(listPermission);
    }

}
