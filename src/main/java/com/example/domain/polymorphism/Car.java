package com.example.domain.polymorphism;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by arahansa on 2016-01-02.
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Car {

    @Id
    @GeneratedValue
    @Column(name="CAR_ID")
    Long id;

    String name;
    int price;

}
