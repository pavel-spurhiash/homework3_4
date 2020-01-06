package com.gmail.pashasimonpure.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.repository.CarRepository;
import com.gmail.pashasimonpure.repository.model.Car;
import com.gmail.pashasimonpure.repository.model.CarModelEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarRepositoryImpl implements CarRepository {

    private static final Logger logger = LogManager.getLogger(CarRepositoryImpl.class);

    @Override
    public Car add(Connection connection, Car car) throws SQLException {

        String sql = "INSERT INTO car(name, car_model, engine_capacity) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, car.getName());
            preparedStatement.setString(2, car.getCarModel().toString());
            preparedStatement.setInt(3, car.getEngineCapacity());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
            }
            return car;
        }

    }

    @Override
    public List<Car> findAllByEngineCapacity(Connection connection, int engineCapacity) throws SQLException {

        String sql = "SELECT * FROM car WHERE engine_capacity=?";
        List<Car> carList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, engineCapacity);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Car car = buildCar(resultSet);
                    carList.add(car);
                }
            }
        }
        return carList;
    }

    @Override
    public boolean deleteAllByMinEngineCapacity(Connection connection) throws SQLException {

        String sql = "DELETE FROM car WHERE engine_capacity = (SELECT * FROM (SELECT MIN(engine_capacity) FROM car) AS min)";

        try (Statement statement = connection.createStatement()) {

            return statement.execute(sql);

        }
    }

    @Override
    public int getCountByEngineCapacity(Connection connection, int engineCapacity) throws SQLException {

        String sql = "SELECT COUNT(*) FROM car WHERE engine_capacity=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, engineCapacity);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
                return count;
            }

        }

    }

    @Override
    public int updateTitleByEngineCapacity(Connection connection, int engineCapacity, String newName) throws SQLException {

        String sql = "UPDATE car SET name = ? WHERE engine_capacity = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, engineCapacity);

            Integer affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating car failed, no rows affected.");
            }

            return affectedRows;
        }
    }

    private Car buildCar(ResultSet resultSet) throws SQLException {

        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        CarModelEnum carModel = CarModelEnum.valueOf(resultSet.getString("car_model"));
        Integer engineCapacity = resultSet.getInt("engine_capacity");

        return Car.newBuilder()
                .id(id)
                .name(name)
                .carModel(carModel)
                .engineCapacity(engineCapacity)
                .build();
    }

}