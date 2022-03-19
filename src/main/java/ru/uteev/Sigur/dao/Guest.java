package ru.uteev.Sigur.dao;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Guest")
public class Guest extends Person {
    @Column(name = "employeeId", length = 64,   nullable = false)
    private Integer employeeId;

    @Temporal(TemporalType.DATE)
    @Column(name = "visitTime", nullable = false)
    private Date visitTime;

    public Guest() {
    }

    public Guest(Integer employeeId, Date visitTime) {
        this.employeeId = employeeId;
        this.visitTime = visitTime;
    }

    //todo
    public Guest(TYPE type, Integer employeeId, Date visitTime) {
        super.setType(type);
        this.employeeId = employeeId;
        this.visitTime = visitTime;
    }

    public int getId() {
        return super.getId();
    }
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "employeeID =" + employeeId +
                ", visitTime =" + visitTime +
                '}';
    }
}
