package spring.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import spring.security.entity.person.Person;
import spring.security.entity.person.PersonRepository;
import spring.security.entity.person.SecurePersonRepository;
import spring.security.entity.system.SystemUser;
import spring.security.entity.system.SystemUserRepository;

import java.util.List;

import static java.util.Collections.singleton;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {})
@ActiveProfiles
@AutoConfigureMockMvc
public class SpringSecureTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SecurePersonRepository securePersonRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    private SystemUser systemUser1;
    private SystemUser systemUser2;
    private SystemUser systemUser3;

    private Person person1;
    private Person person2;
    private Person person3;

    private UsernamePasswordAuthenticationToken usernameToken1;
    private UsernamePasswordAuthenticationToken usernameToken2;
    private UsernamePasswordAuthenticationToken usernameToken3;


    @Before
    public void setUp() {
        systemUser1 = systemUserRepository.save(new SystemUser("SystemUserName1", "LastNameSystemUser1"));
        systemUser2 = systemUserRepository.save(new SystemUser("SystemUserName2", "LastNameSystemUser2"));
        systemUser3 = systemUserRepository.save(new SystemUser("SystemUserName3", "LastNameSystemUser3"));

        person1 = personRepository.save(new Person("PersonName1", "PersonLastName1", systemUser1));
        person2 = personRepository.save(new Person("PersonName2", "PersonLastName2", systemUser1));
        person3 = personRepository.save(new Person("PersonName3", "PersonLastName3", systemUser2));

        usernameToken1 = new UsernamePasswordAuthenticationToken(systemUser1, "CREDENCIALES");
        usernameToken2 = new UsernamePasswordAuthenticationToken(systemUser2, "CREDENCIALES");
        usernameToken3 = new UsernamePasswordAuthenticationToken(
                systemUser3,
                "CREDENCIALES",
                singleton( new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

    }

    @Test
    public void updatePerson() {
        SecurityContextHolder.getContext().setAuthentication(usernameToken3);
        securePersonRepository.updatePerson();;
        List<Person> personasUsernameToken3 = personRepository.findAll();
        for(Person person : personasUsernameToken3) {
            System.out.println();
        }
    }



    @Test
    public void findPersonSecurityUser() {
        SecurityContextHolder.getContext().setAuthentication(usernameToken2);
        List<Person> personasUsernameToken2 = securePersonRepository.findPersonSecurityUser();
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(systemUser1, "CREDENCIALES"));
        List<Person> personasSysmeUser1 = securePersonRepository.findPersonSecurityUser();
    }

    @Test
    public void findPersonSecurityUserByAdmin() {
        SecurityContextHolder.getContext().setAuthentication(usernameToken3);
        List<Person> personasUsernameToken3 = securePersonRepository.findPersonSecurityUser();
    }

    @Test
    public void findPersonSecurityUserByPrincipalId() {
        SecurityContextHolder.getContext().setAuthentication(usernameToken2);
        List<Person> personasUsernameToken2 = securePersonRepository.findPersonSecurityUserByPrincipalId();
        SecurityContextHolder.getContext().setAuthentication(usernameToken1);
        List<Person> personasusernameToken1 = securePersonRepository.findPersonSecurityUserByPrincipalId();
    }

    @Test
    public void methodTest() {
        SecurityContextHolder.getContext().setAuthentication(usernameToken3);
        List<Person> personasUsernameToken3 = securePersonRepository.findPersonSecurityUserByPrincipalId();
    }


}
