package com.kuehnenagel.city.service;

import com.kuehnenagel.city.pojo.dto.CityDto;
import org.springframework.data.domain.Page;

public interface CityService {

    /**
     * Gets the default page of cities
     * @return a page of cities
     */
    Page<CityDto> getCities();

    /**
     * Retrieves a page of cities based on the specified page and size
     * @param page the number of the page to retrieve
     * @param size the number of elements per page to retrieve
     * @return a page of cities
     */
    Page<CityDto> getCitiesPaginated(Integer page, Integer size);

    /**
     * Updates a city's information
     * It does not override the whole city, it updates its attributes
     */
    void updateCity(Long id, CityDto cityDto);

    /**
     * Gets a city's information based on the provided name
     * @param name
     * @return the info of the city that matches with the name
     */
    CityDto getCityByName(String name);

    /**
     * Gets a city's information based on the provided id
     * @param id
     * @return the info of the city that matches with the id
     */
    CityDto getCityById(Long id);

    /**
     * Loads cities in app's database
     */
    void loadCities();

}
