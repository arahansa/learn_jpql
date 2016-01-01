package com.example.domain.polymorphism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by arahansa on 2016-01-02.
 */
@Data
@Entity
@DiscriminatorValue("S")
@AllArgsConstructor
@ToString(callSuper = true)
public class SportsCar extends Car {

    private int turboPower;

    public SportsCar() {
    }

    public SportsCar(String name, int price, int turboPower) {
        this.name = name;
        this.price = price;
        this.turboPower = turboPower;
    }
}
