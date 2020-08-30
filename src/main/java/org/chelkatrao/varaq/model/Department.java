package org.chelkatrao.varaq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.chelkatrao.varaq.model.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "code", length = 5, unique = true, nullable = false)
    private String code;

    @Column(name = "short_name", length = 32)
    @Size(min = 3, max = 255, message = "Short name size must be between 3 and 255")
    protected String shortName;

    @Column(name = "is_deleted", nullable = false)
    @JsonIgnore
    private Integer isDeleted = 0;

    @Column(name = "address")
    @Size(max = 255, message = "Short name size must be between 0 and 255")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "full_name")
    private String accountantFullName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Department parent;

}
