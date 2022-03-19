package ru.uteev.Sigur.dao;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "Person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "card", length = 64,   nullable = false)
    private UUID card = UUID.randomUUID();

    @Column(name = "type")
    private TYPE type;

    public Person() {
    }

    public Person(TYPE type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getCARD() {
        return card;
    }

    public void setCard(UUID card) {
        this.card = card;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Person {" +
                "Id = " + id +
                ", CARD = " + card +
                ", type = " + type +
                '}';
    }
}

