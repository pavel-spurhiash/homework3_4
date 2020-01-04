package com.gmail.pashasimonpure;

import com.gmail.pashasimonpure.service.HomeWorkService;
import com.gmail.pashasimonpure.service.impl.FirstTaskImpl;
import com.gmail.pashasimonpure.service.impl.SecondTaskImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        HomeWorkService homeWorkService = new FirstTaskImpl();
        homeWorkService.runTask();

        homeWorkService = new SecondTaskImpl();
        homeWorkService.runTask();
    }

}