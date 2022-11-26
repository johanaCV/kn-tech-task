package com.kuehnenagel.city.util.csv;

import com.kuehnenagel.city.pojo.csv.CityCsvDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityCsvParser implements CsvParser<CityCsvDto> {

    @Value("${csv.cities.file.name:cities.csv}")
    private String fileName;

    private static final Logger LOGGER = LoggerFactory.getLogger(CityCsvParser.class);

    @Override
    public List<CityCsvDto> getObjectsFromCsv() {
        List<CityCsvDto> entities = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName)))  {

            CsvToBean<CityCsvDto> cb = new CsvToBeanBuilder<CityCsvDto>(inputStreamReader)
                    .withType(CityCsvDto.class).build();

            entities = cb.parse();
        } catch (RuntimeException re) {
            LOGGER.error("Unexpected error while reading cities from file {}", fileName, re);
        }
        catch (IOException ioe) {
            LOGGER.error("Input/Output exception while processing file {}", fileName, ioe);
        }

        return entities;
    }
}
