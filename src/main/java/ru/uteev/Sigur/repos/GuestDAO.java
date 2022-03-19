package ru.uteev.Sigur.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.uteev.Sigur.dao.Employee;
import ru.uteev.Sigur.dao.Guest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GuestDAO extends CrudRepository<Guest, Integer> {

    public List<Guest> findByVisitTime(Date visitTime);
    public Guest findByEmployeeId(int employeeId);

    //@Query()
    //public Employee findByCard(UUID card);

}
