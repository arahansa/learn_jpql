package com.example.jpql;

import com.example.domain.polymorphism.ArmyCar;
import com.example.domain.polymorphism.Car;
import com.example.domain.polymorphism.ElectronicCar;
import com.example.domain.polymorphism.SportsCar;
import com.example.repository.CarRepository;
import com.example.service.jpql.PolyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by arahansa on 2016-01-02.
 */
@Transactional
public class PolymophismTest   extends TestJPQLConfig  {
    
    @Autowired
    CarRepository carRepository;

    @Autowired
    PolyService polyService;

    @Test
    @Rollback(value = false)
    public void testPolymophism() throws Exception{
        // save
        ElectronicCar eCar = new ElectronicCar("전기차", 100, 1000);
        SportsCar sCar = new SportsCar("스포츠카", 150, 90);
        ArmyCar aCar = new ArmyCar("군대카", 10, 100);
        List<Car> cars = Arrays.asList(eCar, sCar, aCar);
        carRepository.save(cars);
        assertEquals(3L, carRepository.count());

        // see queries depends on joinTypes
        List carList = polyService.getCars("select c from Car c");
        carList.forEach(n-> System.out.println(n));

        // type
        carList = polyService.getCars("select c from Car c where type(c) IN (ArmyCar, SportsCar)");
        carList.forEach(n->assertTrue(n instanceof ArmyCar || n instanceof SportsCar));
    }
}
