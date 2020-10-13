package org.chelkatrao.varaq.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.chelkatrao.varaq.model.base.BaseEntity;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Column(name = "role_name", length = 64)
    private String roleName;

    @Column(name = "role_info", length = 64, unique = true)
    private String roleInfo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "u_roles_permissions",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"
            )
    )
    private Set<Permission> permissions = new LinkedHashSet<>();

}
