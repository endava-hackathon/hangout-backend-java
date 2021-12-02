package hangoutbk.repositories;

import hangoutbk.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, UUID> {


    List<Person> findAll();

    Person findByEmail(String username);

    Person save(Person person);

    @Override
    void deleteById(UUID uuid);

    /**
     * Example: Write Custom Query
     */
   /* @Query(value = "SELECT p " +
            "FROM Person p " +
            "WHERE p.name LIKE ':name%' ")
    List<Person> findAllByNameStartingWith(@Param("name") String name);*/

}
