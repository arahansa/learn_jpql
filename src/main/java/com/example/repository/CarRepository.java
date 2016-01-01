package com.example.repository;

import com.example.domain.polymorphism.Car;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arahansa on 2016-01-02.
 */
public interface CarRepository extends JpaRepository<Car, Long>{
}
