package com.example.domain.polymorphism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by arahansa on 2016-01-02.
 */
@Data
@Entity
@DiscriminatorValue("E")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
public class ElectronicCar extends Car {

    private int empPower;

    public ElectronicCar() {
    }

    public ElectronicCar(String name, int price, int empPower) {
        this.name = name;
        this.price = price;
        this.empPower = empPower;
    }
}
