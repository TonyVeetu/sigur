package ru.uteev.Sigur.dao;


import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="Employee")
public class Employee extends Person {

    @Temporal(TemporalType.DATE)
    @Column(name = "hireTime", nullable = false)
    private Date hireTime;//random

    @Temporal(TemporalType.DATE)
    @Column(name = "fireTime", nullable = true)
    private Date fireTime;//current time!

    @Column(name = "depId",  nullable = false)
    private int departmentID;

    private String departmentName;

    public Employee() {
    }

    public Employee(TYPE type,  Date hireTime, Date fireTime, int departmentID, String departmentName) {
        super.setType(type);
        this.hireTime = hireTime;
        this.fireTime = fireTime;
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getId() {
        return super.getId();
    }

    public Date getHireTime() {
        return hireTime;
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = hireTime;
    }

    public Date getFireTime() {
        return fireTime;
    }

    public void setFireTime(Date fireTime) {
        this.fireTime = fireTime;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public String toString() {
        return "Employee{" +
                " hireTime = " + hireTime +
                ", fireTime = " + fireTime +
                ", departmentID = " + departmentID +
                ", departmentName = " + departmentName +
                '}';
    }


}
