package org.chelkatrao.varaq.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class DepartmentDto {

    private Long id;
    private String name;
    private String code;
    private String shortName;
    private String address;
    private String phone;
    private String accountantFullName;
    private Long parentId;

    public DepartmentDto(Long id, String name, String code, String shortName, String address, String phone, String accountantFullName,Long parentId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.shortName = shortName;
        this.address = address;
        this.phone = phone;
        this.accountantFullName = accountantFullName;
        this.parentId = parentId;
    }

}
