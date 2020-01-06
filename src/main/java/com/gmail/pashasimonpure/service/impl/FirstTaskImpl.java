package com.gmail.pashasimonpure.service.impl;

import java.io.*;

import com.gmail.pashasimonpure.service.HomeWorkService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FirstTaskImpl implements HomeWorkService {

    private static final Logger logger = LogManager.getLogger(FirstTaskImpl.class);
    private static final String INPUT_FILE_NAME = "input.txt";

    @Override
    public void runTask() {

        logger.debug("\nFIRST TASK:");

        try {
            File input = new File(INPUT_FILE_NAME);

            if (!input.exists()) {
                input.createNewFile();
                logger.debug("created empty " + INPUT_FILE_NAME);
            }

            String[] QueryArray = readCommandsFromFile(input);
            if (QueryArray == null) {
                logger.error(INPUT_FILE_NAME + " is empty, exit.");
                return;
            }

            for (String command : QueryArray) {
                logger.info(command);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("can't create " + INPUT_FILE_NAME);
        }
    }

    private String[] readCommandsFromFile(File file) {

        if (!file.exists()) {
            return null;
        }

        String[] arr;

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {

            String content = "";
            String line = input.readLine();

            while (line != null) {
                content = content + line + " ";
                line = input.readLine();
            }

            if (content.length() == 0) {
                return null;
            }

            arr = content.replaceAll("\\s{2,}", " ").trim().split(";");

            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].trim();
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("read file error " + file.getName());
        }

        return arr;
    }

}