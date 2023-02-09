package spring.security.entity.person;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface SecurePersonRepository extends Repository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.owner.name LIKE ?#{hasRole('ROLE_ADMIN') ? '%' : principal.name}")
    List<Person> findPersonSecurityUser();


    @Query("SELECT p FROM Person p WHERE p.owner.id LIKE ?#{principal.id} or 1=?#{hasRole('ROLE_ADMIN') ? 1:0}")
    List<Person> findPersonSecurityUserByPrincipalId();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Person p set p.name = upper(p.name), p.modifiedBy = :#{#security.principal}, p.modified = :#{new java.util.Date()}")
    void updatePerson();


}
