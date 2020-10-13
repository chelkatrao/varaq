package org.chelkatrao.varaq.dto.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chelkatrao.varaq.enums.Gender;
import org.chelkatrao.varaq.enums.MarriedStatusEnum;
import org.chelkatrao.varaq.model.base.Person;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {

    Long id;
    String firstName;
    String lastName;
    String middleName;
    Date dateOfBirth;
    String region;
    Gender gender;
    String mobile;
    String email;
    String address;
    String address2;
    MarriedStatusEnum marriageStatus;
    String citizen;

    public PersonDto(Long id, String firstName, String lastName, String middleName, Date dateOfBirth, String region, Gender gender, String mobile, String email, String address, String address2, MarriedStatusEnum marriageStatus, String citizen) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.region = region;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.address2 = address2;
        this.marriageStatus = marriageStatus;
        this.citizen = citizen;
    }

}
