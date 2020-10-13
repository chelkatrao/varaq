package org.chelkatrao.varaq.model.base;

import lombok.Getter;
import lombok.Setter;
import org.chelkatrao.varaq.enums.Gender;
import org.chelkatrao.varaq.enums.MarriedStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "people")
@Inheritance
@DiscriminatorColumn(name = "person_type")
@Getter
@Setter
public class Person extends BaseEntity {

    @Column(name = "first_name", length = 32)
    @Size(min = 1, max = 32, message = "First name must be between 3 and 32")
    protected String firstName;

    @Column(name = "last_name", length = 32)
    @Size(min = 1, max = 32, message = "Last name must be between 3 and 32")
    protected String lastName;

    @Column(name = "middle_name", length = 32)
    @Size(min = 0, max = 32, message = "Middle name size must be between 3 and 32")
    protected String middleName;

    @Column(name = "dob")
    private Date dateOfBirth;// tug'ulgan sanasi

    @Column(name = "region")
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "mobile", length = 16)
    @Size(max = 16, message = "Mobile size must be between 0 and 16")
    private String mobile;

    @Column(name = "email", length = 32)
    @Size(max = 32, message = "Email size must be between 0 and 32")
    @Email
    private String email;

    @Column(name = "address")
    @Size(max = 255)
    private String address;

    @Column(name = "address2", length = 64)
    @Size(max = 64, message = "Address2 size must be between 0 and 64")
    private String address2;

    @Column(name = "marriage_status")
    @Enumerated(EnumType.STRING)
    private MarriedStatusEnum marriageStatus;

    @Column(name = "citizen_id")
    private String citizen;

}
