package com.kuehnenagel.city.util.csv;

import java.util.List;

public interface CsvParser<T> {

    /**
     * Gets a list of objects of type T from a csv
     * @return
     */
    List<T> getObjectsFromCsv();
}
