package com.gmail.pashasimonpure.service;

import java.util.List;

import com.gmail.pashasimonpure.repository.model.Car;

public interface CarService {

    Car add(Car car);

    List<Car> addAll(List<Car> cars);

    List<Car> findAllByEngineCapacity(int searchEngineCapacity);

    boolean deleteAllByMinEngineCapacity();

    int getCountByEngineCapacity(int searchEngineCapacity);

    int updateTitleByEngineCapacity(int searchEngineCapacity, String newName);

}