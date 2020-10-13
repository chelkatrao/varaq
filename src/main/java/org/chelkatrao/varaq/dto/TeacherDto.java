package org.chelkatrao.varaq.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chelkatrao.varaq.dto.base.PersonDto;
import org.chelkatrao.varaq.enums.Gender;
import org.chelkatrao.varaq.enums.MarriedStatusEnum;
import org.chelkatrao.varaq.model.base.Person;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class TeacherDto extends PersonDto {
    public TeacherDto(Long id, String firstNameString, String lastName, String middleName, Date dateOfBirth, String region, Gender gender, String mobile, String email, String address, String address2, MarriedStatusEnum marriageStatus, String citizen) {
        super(id, firstNameString, lastName, middleName, dateOfBirth, region, gender, mobile, email, address, address2, marriageStatus, citizen);
    }
}
