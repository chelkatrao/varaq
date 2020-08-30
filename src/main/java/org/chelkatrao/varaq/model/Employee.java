package org.chelkatrao.varaq.model;

import lombok.Getter;
import lombok.Setter;
import org.chelkatrao.varaq.enums.EmployeeStatusEnum;
import org.chelkatrao.varaq.model.base.Person;

import javax.persistence.*;
import java.util.Date;


@Entity
@DiscriminatorValue("employees")
@Getter
@Setter
public class Employee extends Person {

    @Column(name = "start_work_date")
    private Date startWorkDate;

    @Column(name = "passport_Number")
    private String passportNumber;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "employee_status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatusEnum employeeStatus;

    @Column(name = "hr_dismiss_date")
    private Date dismissDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
