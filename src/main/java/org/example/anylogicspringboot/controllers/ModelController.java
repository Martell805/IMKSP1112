package org.example.anylogicspringboot.controllers;

import com.anylogic.engine.Engine;
import org.example.anylogicspringboot.domain.dto.AbstractResponse;
import org.example.anylogicspringboot.domain.dto.DataRequest;
import org.example.anylogicspringboot.domain.dto.ModelRequest;
import org.example.anylogicspringboot.domain.dto.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import model.CustomExperiment;
import model.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
public class ModelController {
    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @CrossOrigin("*")
    public AbstractResponse runModel(@RequestBody ModelRequest modelRequest) {
        createModel(modelRequest);
        return new SuccessResponse("Model worked successfully!");
    }

    @PostMapping(value = "/data", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin("*")
    public List<Map<String, String>> getData(@RequestBody DataRequest dataRequest) throws IOException {
        return get(dataRequest.getScenarioNumber());
    }

    private void createModel(ModelRequest request) {
        Random rand = new Random(System.currentTimeMillis());
        CustomExperiment customExperiment = new CustomExperiment(null);
        Engine engine = customExperiment.createEngine();
        engine.setDefaultRandomGenerator(rand);
        Main main = new Main(engine, null, null);
        main.setParametersToDefaultValues();
        main.setDefaultRandomGenerator(rand);
        engine.start(main);

        Date date = new Date();
        Calendar date2 = Calendar.getInstance();
        date2.setTime(date);
        date2.add(Calendar.YEAR, 30);
        Date stop_date = date2.getTime();

        engine.setStartDate(date);
        engine.setStopDate(stop_date);
        engine.setRealTimeMode(false);

        main.Сценарий = request.getScenarioNumber();
        main.Темп_бурения = request.getDrillingRate();
        main.Цена_на_нефть = request.getOilPrice();
        main.Курс_доллара = request.getExchangeRate();

        while (engine.getTime() < 30) {
            engine.step();
        }
        engine.stop();
    }

    public static List<Map<String, String>> get(Integer scenarioNumber) throws IOException {
        final var CSV_FILE = ".\\data.csv";
        try (Stream<String> lines = Files.lines(Paths.get(CSV_FILE))) {
            return lines
                    .map(line -> Arrays.asList(line.split(",")))
                    .filter(line -> Integer.valueOf(line.get(1)).equals(scenarioNumber))
                    .map(line -> Map.of("year", line.get(0), "profit", line.get(2)))
                    .toList();
        }
    }
}

