package spring.security.entity.person;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.security.entity.system.SystemUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastName;
    private Date modified;

    @ManyToOne
    private SystemUser modifiedBy;

    @ManyToOne
    private SystemUser owner;

    public Person() {
        owner = null;
    }


    public Person(String personName, String personLastName, SystemUser systemUser) {
        setName(personName);
        setLastName(personLastName);
        setOwner(systemUser);
    }

}
