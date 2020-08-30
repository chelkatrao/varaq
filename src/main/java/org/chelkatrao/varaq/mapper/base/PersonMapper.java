package org.chelkatrao.varaq.mapper.base;

import org.chelkatrao.varaq.dto.base.PersonDto;
import org.chelkatrao.varaq.model.base.Person;
import org.chelkatrao.varaq.service.UserSession;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    private UserSession userSession;

    public PersonMapper(UserSession userSession) {
        this.userSession = userSession;
    }

    public PersonDto toPersonDto(PersonDto personDto, Person person) {
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setMiddleName(person.getMiddleName());
        personDto.setDateOfBirth(person.getDateOfBirth());
        personDto.setRegion(person.getRegion());
        personDto.setGender(person.getGender());
        personDto.setMobile(person.getMobile());
        personDto.setEmail(person.getEmail());
        personDto.setAddress(person.getAddress());
        personDto.setAddress2(person.getAddress2());
        personDto.setMarriageStatus(person.getMarriageStatus());
        personDto.setCitizen(person.getCitizen());
        return personDto;
    }

    public Person toPerson(PersonDto personDto, Person person) {
        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setMiddleName(personDto.getMiddleName());
        person.setDateOfBirth(personDto.getDateOfBirth());
        person.setRegion(personDto.getRegion());
        person.setMobile(personDto.getMobile());
        person.setEmail(personDto.getEmail());
        person.setAddress(personDto.getAddress());
        person.setAddress2(personDto.getAddress2());
        person.setCitizen(personDto.getCitizen());
        person.setCreateBy(userSession.getUser().getUsername());
        return person;
    }


}
