package org.chelkatrao.varaq.dto;

import lombok.*;
import org.chelkatrao.varaq.dto.base.PersonDto;
import org.chelkatrao.varaq.enums.EmployeeStatusEnum;
import org.chelkatrao.varaq.enums.MarriedStatusEnum;
import org.chelkatrao.varaq.model.Employee;
import org.chelkatrao.varaq.model.base.Person;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto extends PersonDto {

    private Date startWorkDate;
    private String passportNumber;
    private String taxNumber;
    private EmployeeStatusEnum employeeStatus;
    private Date dismissDate;
    private Long departmentId;

    public EmployeeDto(Date startWorkDate,
                       String passportNumber,
                       String taxNumber,
                       EmployeeStatusEnum employeeStatus,
                       Date dismissDate, Long departmentId,
                       Long id, String firstNameString, String lastName, String middleName, Date dateOfBirth, String region, Person.Gender gender, String mobile, String email, String address, String address2, MarriedStatusEnum marriageStatus, String citizen) {
        super(id, firstNameString, lastName, middleName, dateOfBirth, region, gender, mobile, email, address, address2, marriageStatus, citizen);
        this.departmentId = departmentId;
        this.startWorkDate = startWorkDate;
        this.passportNumber = passportNumber;
        this.taxNumber = taxNumber;
        this.employeeStatus = employeeStatus;
        this.dismissDate = dismissDate;
    }

}
