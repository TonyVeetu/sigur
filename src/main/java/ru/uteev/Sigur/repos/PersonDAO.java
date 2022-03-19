package ru.uteev.Sigur.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uteev.Sigur.dao.Employee;
import ru.uteev.Sigur.dao.Person;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonDAO extends CrudRepository<Person, Integer> {

    public Person findByCard(UUID card);
    //public Person findById(int id);

}
