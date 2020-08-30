package org.chelkatrao.varaq.model;

import lombok.Getter;
import lombok.Setter;
import org.chelkatrao.varaq.model.base.Person;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Students extends Person {

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
