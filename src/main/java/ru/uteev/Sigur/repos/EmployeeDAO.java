package ru.uteev.Sigur.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uteev.Sigur.dao.Employee;


import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeDAO extends CrudRepository<Employee, Integer> {

    public List<Employee> findAllByFireTimeIsNull();
    public List<Employee> findAllByFireTimeIsNotNull();
    public List<Employee> findAll();

    @Query(value="SELECT id FROM person p RIGHT JOIN employee e on p.id=e.id where p.card= :card", nativeQuery=true)
    public int findByCard(UUID card);

    @Query(value="SELECT id FROM person where p.card= :card", nativeQuery=true)
    public int findIdByCard(UUID card);

}
