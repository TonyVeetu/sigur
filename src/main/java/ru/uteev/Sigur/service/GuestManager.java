package ru.uteev.Sigur.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.uteev.Sigur.dao.Employee;
import ru.uteev.Sigur.dao.Guest;
import ru.uteev.Sigur.dao.TYPE;
import ru.uteev.Sigur.repos.EmployeeDAO;
import ru.uteev.Sigur.repos.GuestDAO;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GuestManager {
    final static Logger logger= LoggerFactory.getLogger(GuestManager.class);

    private AtomicInteger[] meetsArray = new AtomicInteger[VirtualTime.countDayInYear];

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private GuestDAO guestDAO;

    @Autowired
    private VirtualTime virtualTime;

    @Autowired
    private PassEmulator passEmulator;

    //первая ситуация достали всех неуволенных сотрудников и создали для них гостей!
    //вторая сотрудник стал уволенным а встреча уже назначена!

    public void work() {
        // все не уволенные сотрудники!!!
        List<Employee> list = employeeDAO.findAllByFireTimeIsNull();

        int length = list.size();
        for (int i = 0; i < length; i++) {
            Employee employee = list.get(i);

            int id = employee.getId();
            Guest guestCurrent = guestDAO.findByEmployeeId(id);
            // найти тех к кому НЕ назначена встреча!!!
            if (guestCurrent == null) {
                int k = (int) (Math.random() * 2);
                if (k >= 1) {//вероятность 1/2
                    Guest guest = getGuest(employee);
                    addGuest(guest, employee);
                }
            }
        }

        List<Employee> employees = employeeDAO.findAllByFireTimeIsNotNull();
        //получаем список уволенных сотрудников
        //найти гостя!
        //если он существует, то написать что отменяем встречу!
        //todo сравнить дату встречи и увольнения!
        for (int i = 0; i < employees.size(); i ++) {
            Employee employee = employees.get(i);
            int employeeId = employee.getId();
            Optional<Guest> guestOptional = Optional.ofNullable(guestDAO.findByEmployeeId(employeeId));
            if (guestOptional != null) {
                Guest guest = guestOptional.get();
                Date fire = employee.getFireTime();
                Date vizit = guest.getVisitTime();
                if (vizit.after(fire)) {
                    logger.info("Встреча гостя " + guest.getId()
                            + " с сотрудником " + employee.getId() + " отменена!"
                            + "Отдел " + employee.getDepartmentName()
                            + ". Дата встречи " + guest.getVisitTime()
                            + " дата увольнения сотрудника " + employee.getFireTime());
                }
            }
        }

    }

    //todo описание
    public void addGuest(Guest guest, Employee employee) {
        guestDAO.save(guest);

        Date visit = guest.getVisitTime();
        Date current = virtualTime.getCurrentDate();
        int daysBeforeMeet = virtualTime.getDifferentInDayBetweenDate(current, visit);

        logger.info(employee.toString());
        logger.info(guest.toString());
        //todo индекс -24!!!
        int daysToMeetAfterStartTime = virtualTime.getDifferentInDayBetweenDate(VirtualTime.startTime.getTime(), visit);

        logger.info("StartTime = "+ String.valueOf(VirtualTime.startTime.getTime()));
        logger.info("Visit Time = "+ String.valueOf(visit));

        logger.info("Гостю (id = " + guest.getId() + ")"
                + " назначена встреча к сотруднику (id = " + employee.getId() + "). "
                + "Отдел " + employee.getDepartmentName() + ". "
                + "Дата " + guest.getVisitTime()
                + " до встречи осталось = " + daysBeforeMeet);
    }


    public Guest getGuest(Employee employee) {
        Date visitDate = virtualTime.getRandomVisitDateAfterHire(employee);
        return new Guest(TYPE.GEST, employee.getId(), visitDate);
    }

}
