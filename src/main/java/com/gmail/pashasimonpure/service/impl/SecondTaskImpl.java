package com.gmail.pashasimonpure.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gmail.pashasimonpure.repository.model.Car;
import com.gmail.pashasimonpure.repository.model.CarModelEnum;
import com.gmail.pashasimonpure.service.CarService;
import com.gmail.pashasimonpure.service.HomeWorkService;
import com.gmail.pashasimonpure.service.util.RandomUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SecondTaskImpl implements HomeWorkService {

    private static final Logger logger = LogManager.getLogger(FirstTaskImpl.class);

    @Override
    public void runTask() {
        logger.debug("\nSECOND TASK:");

        CarService carService = new CarServiceImpl();
        List<Car> carList = generateCars(10);
        Integer randomCapacity = RandomUtil.getRandomInt();

        carService.addAll(carList);

        carList = carService.findAllByEngineCapacity(randomCapacity);

        logger.debug("Cars with engine capacity " + randomCapacity + ": ");

        if (carList.isEmpty()) {
            logger.debug("empty");
        }

        for (Car car : carList) {
            logger.info(car);
        }

        carService.deleteAllByMinEngineCapacity();

        logger.info("Count cars with capacity " + randomCapacity + ": " + carService.getCountByEngineCapacity(randomCapacity));

        logger.info("Names updated: " + carService.updateTitleByEngineCapacity(randomCapacity, "Good Capacity"));

    }

    private List<Car> generateCars(int count) {
        List<Car> carList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Car car = Car.newBuilder()
                    .name("Car#" + RandomUtil.getRandomIntBetween(100, 999))
                    .carModel(CarModelEnum.values()[new Random().nextInt(CarModelEnum.values().length)])
                    .engineCapacity(RandomUtil.getRandomIntBetween(1, 10))
                    .build();
            carList.add(car);
        }
        return carList;

    }

}