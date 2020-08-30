package org.chelkatrao.varaq.enums;

import org.chelkatrao.varaq.model.Employee;

public enum UserPermissionEnum {

    SUPER_ADMIN_READ("super_admin:read"),
    SUPER_ADMIN_WRITE("super_admin:write"),

    DEPARTMENT_SUPER_ADMIN_READ("department_super_admin_admin:read"),
    DEPARTMENT_SUPER_ADMIN_WRITE("department_super_admin_admin:write"),

    DEPARTMENT_ADMIN_READ("department_admin_read:read"),
    DEPARTMENT_ADMIN_WRITE("department_admin_read:read"),

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),

    USER_READ("user:read"),
    USER_WRITE("user:write"),

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),

    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),

    TEACHER_READ("teacher:read"),
    TEACHER_WRITE("teacher:write");

    private String permission;

    UserPermissionEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
