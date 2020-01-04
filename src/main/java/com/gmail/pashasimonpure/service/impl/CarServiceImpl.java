package com.gmail.pashasimonpure.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.gmail.pashasimonpure.repository.CarRepository;
import com.gmail.pashasimonpure.repository.ConnectionRepository;
import com.gmail.pashasimonpure.repository.impl.CarRepositoryImpl;
import com.gmail.pashasimonpure.repository.impl.ConnectionRepositoryImpl;
import com.gmail.pashasimonpure.repository.model.Car;
import com.gmail.pashasimonpure.service.CarService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarServiceImpl implements CarService {

    private static final String CONFIG_FILE_LOCATION = "config.properties";
    private static final Logger logger = LogManager.getLogger(CarService.class);
    private ConnectionRepository connectionRepository = new ConnectionRepositoryImpl(CONFIG_FILE_LOCATION);
    private CarRepository carRepository = new CarRepositoryImpl();

    @Override
    public Car add(Car car) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                car = carRepository.add(connection, car);
                connection.commit();
                return car;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public List<Car> addAll(List<Car> cars) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                for (Car car : cars) {
                    carRepository.add(connection, car);
                }
                connection.commit();
                return cars;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Car> findAllByEngineCapacity(int searchEngineCapacity) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Car> carList = carRepository.findAllByEngineCapacity(connection, searchEngineCapacity);
                connection.commit();
                return carList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteAllByMinEngineCapacity() {
        boolean isDeleted = false;

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                isDeleted = carRepository.deleteAllByMinEngineCapacity(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return isDeleted;
    }

    @Override
    public int getCountByEngineCapacity(int searchEngineCapacity) {

        int count = 0;

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                count = carRepository.getCountByEngineCapacity(connection, searchEngineCapacity);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return count;
    }

    @Override
    public int updateTitleByEngineCapacity(int searchEngineCapacity, String newName) {

        int affectedRows = 0;

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                affectedRows = carRepository.updateTitleByEngineCapacity(connection, searchEngineCapacity, newName);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return affectedRows;
    }

}