package ru.uteev.Sigur.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.uteev.Sigur.dao.Employee;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
INFO:start 1 juanuary 2021 = final 31 december

    1. дает рандомную дату найма сотрудника в пределах диапазона в формате Date!!
    2. внутри себя считает какой идет день!! 1 секунда 1 день!
    3. выставляет время уволнения сотрудника!

**/

@Component
public class VirtualTime {
    final static Logger logger= LoggerFactory.getLogger(VirtualTime.class);
    //24 часа в дне/60 минут в часе/60 секунд в минуте
    public static final int millSecInDay = 24 * 60 * 60 * 1000;
    //24 часа в дне / 60 минут в часе
    public static final int minutesInDay = 24*60;
    public final static int countDayInYear = 356;

    //todo delete
    //@Value("${virtual.Time.start}") //todo use this!
    private String startFromProp;

    /**
     * месяц начинается с нуля
     */
    public static final Calendar startTime = new GregorianCalendar(2022, 0, 1);

    //todo delete
    //@Value("${virtual.Time.finish}")
    //private Date finishTime = new Date(2022,11,31);
    private Calendar finishTime = new GregorianCalendar(2022, 11, 31);

    //@Value("${virtual.Time.countDay}")
    public static int daysInYear = 365;

    //@Value("${virtual.Time.delayInSec}")
    private int delayInSec = 1;

    private AtomicInteger currentDay = new AtomicInteger();


    public void work() {
        int currentDays = currentDay.addAndGet(1);
        logger.info("Current day = " + currentDays);
    }

    /**
     * возвращает текущее виртуальное время с рандомным количеством секунд!
    * */
    public Date getCurrentDate() {
        int randomMinutes = getRandomMinutes();
        //todo коррект!!
        Calendar current = new GregorianCalendar(2022, 0, 1);
        int days = currentDay.get();
        current.add(Calendar.DATE, days);
        //current.add(Calendar.MINUTE, randomMinutes);
        return current.getTime();
    }

    public int getCurrentDay() {
        return currentDay.get();
    }

    /**
     * @param days - количество дней с начала виртуального времени
     * @return дату с рандомным количествои минут в нужном дне!
     */
    public Date getNeededDateWithRandomMinInThisDay(int days) {
        Calendar current = new GregorianCalendar(2022, 0, 1);
        current.add(Calendar.DATE, days);
        current.add(Calendar.MINUTE, getRandomMinutes());
        logger.info("current = " + current.getTime());
        return current.getTime();
    }

    //возвращает рандомное количество минут в течении дня в миллисекундах!!
    //todo тестировать!
    public int getRandomMinutes() {
        int randomMinutes = (int) (Math.random() * minutesInDay);
        return randomMinutes;
    }


    public int getDifferentInDayBetweenDate(Date second, Date first) {
        int daysInMills = (int) (first.getTime() - second.getTime());
        int day = daysInMills/1000/60/60/24;
        return day;
    }

    /**
     *
     * @param employee
     * @return если результат больше @{finishTime}
     * тогда вычитаем рандомное количество дней в пределах 60.
     *
     */

    public Date getRandomVisitDateAfterHire(Employee employee) {
        Date hireDateEmployee = employee.getHireTime();
        //добавить к дате полгода!!
        //проверить не вышло ли это за гранницы finishTime!
        long hireDateEmplMils = hireDateEmployee.getTime();
        //рандомный день в течении пол года!
        int randomDay = (int) (Math.random() * countDayInYear/2);
        long randomDayInMills = randomDay * millSecInDay;

        long result = hireDateEmplMils + randomDayInMills;
        long finishTimeLong = finishTime.getTime().getTime();
        if (result > finishTimeLong) {
            int randomDayMunis = (int) (Math.random() * 60);
            finishTimeLong = finishTimeLong - (randomDayMunis *  millSecInDay);
            return new Date(finishTimeLong);
        }
        return new Date(result);
    }

}
