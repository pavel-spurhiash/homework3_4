CREATE DATABASE IF NOT EXISTS `jd2_homework3-4`;
USE `jd2_homework3-4`;
CREATE TABLE IF NOT EXISTS car
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    car_model       VARCHAR(255) NOT NULL,
    engine_capacity INT          NOT NULL
);