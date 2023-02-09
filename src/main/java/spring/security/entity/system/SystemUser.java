package spring.security.entity.system;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class SystemUser {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastName;

    public SystemUser() {

    }

    public SystemUser(String systemUserName, String lastNameSystemUser) {
        setName(systemUserName);
        setLastName(lastNameSystemUser);
    }

}
