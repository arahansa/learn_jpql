package com.example.domain.polymorphism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by arahansa on 2016-01-02.
 */
@Entity
@DiscriminatorValue("A")
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class ArmyCar extends Car{

    private int loadage;

    public ArmyCar() {
    }

    public ArmyCar(String name, int price, int loadage) {
        this.name = name;
        this.price = price;
        this.loadage = loadage;
    }
}
