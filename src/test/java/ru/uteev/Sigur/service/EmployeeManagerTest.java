package ru.uteev.Sigur.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import ru.uteev.Sigur.dao.Employee;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeManagerTest {
    final static Logger logger= LoggerFactory.getLogger(EmployeeManagerTest.class);
    @Autowired
    private EmployeeManager employeeManager;

    @Test
    void getEmployee() {
        Employee employee = employeeManager.getEmployee();
        logger.info(employee.toString());
    }

}