package ru.uteev.Sigur.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.uteev.Sigur.dao.Employee;
import ru.uteev.Sigur.dao.Guest;
import ru.uteev.Sigur.dao.Person;
import ru.uteev.Sigur.dao.TYPE;
import ru.uteev.Sigur.repos.EmployeeDAO;
import ru.uteev.Sigur.repos.GuestDAO;
import ru.uteev.Sigur.repos.PersonDAO;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class PassEmulator {
    final static Logger logger = LoggerFactory.getLogger(PassEmulator.class);
    private int currentId = 0;

    @Autowired
    private VirtualTime virtualTime;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private GuestDAO guestDAO;

    @Autowired
    private PersonDAO personDAO;

    public void work() {
        Date currentDate = virtualTime.getCurrentDate();
        int value = (int) (Math.random() * 5);
        if (value >= 1) { // вероятность 4/5
            Optional<Person> personOptional = getPerson();
            if (!personOptional.isEmpty()) {
                Person person = personOptional.get();
                TYPE type = person.getType();
                UUID card = person.getCARD();
                Integer id = (Integer) person.getId();
                if (type.equals(TYPE.EMPLOYEE)) {
                    logger.info("Take EMPLOYEE, id = " + id);
                    Optional<Employee> employeeOptional = employeeDAO.findById(id);
                    if (!employeeOptional.isPresent()) {
                        Employee employee = employeeOptional.get();
                        Date fireTime = employee.getFireTime();
                        if (fireTime != null) {
                            logger.info(currentDate
                            + ". Доступ запрещен сотруднику! ID = " + employee.getId()
                            + "Отдел: " + employee.getDepartmentName()
                            + "Карта = " + card);
                        } else {
                            logger.info(currentDate
                            + ". Предоставлен доcтуп сотруднику! ID = " + employee.getId()
                            + "Отдел: " + employee.getDepartmentName()
                            + "Карта = " + card);
                        }
                    }
                } else {
                    logger.info("Take GUEST! id = " + id);
                    Optional<Guest> guestOptional = guestDAO.findById(id);
                    if (!guestOptional.isPresent()) {
                        Guest guest = guestOptional.get();
                        int idEmpl = guest.getEmployeeId();
                        Optional<Employee> employeeOpt = employeeDAO.findById(idEmpl);
                        if (employeeOpt.isPresent()) {
                            Employee employee = employeeOpt.get();
                            Date visitTime = guest.getVisitTime();
                            int dayVisit = visitTime.getDay();
                            int dayCurrentDay = currentDate.getDay();
                            if (dayVisit == dayCurrentDay) {
                                logger.info(currentDate
                                + "Предоставлен доступ гостю! ID = " + guest.getId()
                                + "Пришел к сотруднику ID =" + employee.getId()
                                + "из отдела =" + employee.getDepartmentName()
                                + "Карта + " + card);
                            } else {
                                logger.info(currentDate
                                + "Доступ гостю запрещен! ID = " + guest.getId()
                                + "Карта + " + card);
                            }
                        }
                    }
                }
            }
        } else { // 1/5
            UUID currentCard = UUID.randomUUID();
            logger.info("Поднесена неизвестная карта! " + currentCard);
        }
    }


    private Optional<Person> getPerson() {
        Optional<Person> person = personDAO.findById(currentId);
        currentId++;
        return person;
    }

}