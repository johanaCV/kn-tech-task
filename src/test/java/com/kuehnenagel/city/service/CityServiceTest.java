package com.kuehnenagel.city.service;

import com.kuehnenagel.city.entity.City;
import com.kuehnenagel.city.exception.CityNotFoundException;
import com.kuehnenagel.city.mapper.CityMapper;
import com.kuehnenagel.city.pojo.csv.CityCsvDto;
import com.kuehnenagel.city.pojo.dto.CityDto;
import com.kuehnenagel.city.repository.CityRepository;
import com.kuehnenagel.city.util.csv.CityCsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    CityService cityService;

    @Mock
    CityRepository cityRepository;

    @Mock
    CityCsvParser cityCsvConverter;

    @Mock
    CityMapper cityMapper;

    @BeforeEach
    void init() {
        cityService = new CityServiceImpl(cityRepository, cityMapper,
                cityCsvConverter, 1, 5, "text");
    }

    @Test
    void testGetCitiesDefault() {
        City city1 = new City();
        city1.setPhotoUri("uri1");
        city1.setName("name1");
        city1.setId(1L);

        City city2 = new City();
        city2.setPhotoUri("uri2");
        city2.setName("name2");
        city2.setId(2L);

        Page<City> pageCity = new PageImpl(List.of(city1, city2));

        when(cityRepository.findAll(isA(Pageable.class))).thenReturn(pageCity);
        when(cityMapper.map(isA(City.class))).thenReturn(new CityDto());

        Page<CityDto> pageCityDto = cityService.getCities();

        //call verification
        verify(cityRepository, times(1)).findAll(isA(Pageable.class));
        verify(cityMapper, times(2)).map(isA(City.class));

        assertEquals(2, pageCityDto.getTotalElements());
        assertEquals(1, pageCityDto.getTotalPages());

        //object verification
        pageCityDto.getContent().stream().allMatch(Objects::nonNull);
    }

    @Test
    void testGetCitiesPaginated() {
        City city1 = new City();
        city1.setPhotoUri("uri1");
        city1.setName("name1");
        city1.setId(1L);

        Page<City> pageCity = new PageImpl(List.of(city1));

        when(cityRepository.findAll(isA(Pageable.class))).thenReturn(pageCity);
        when(cityMapper.map(isA(City.class))).thenReturn(new CityDto());

        Page<CityDto> pageCityDto = cityService.getCitiesPaginated(1, 5);

        //call verification
        verify(cityRepository, times(1)).findAll(isA(Pageable.class));
        verify(cityMapper, times(1)).map(isA(City.class));

        assertEquals(1, pageCityDto.getTotalElements());
        assertEquals(1, pageCityDto.getTotalPages());

        //object verification
        pageCityDto.getContent().stream().allMatch(Objects::nonNull);
    }

    @Test
    void testUpdateCity_Found() {
        City city = new City();

        when(cityRepository.findById(isA(Long.class))).thenReturn(Optional.of(city));
        when(cityRepository.save(isA(City.class))).thenReturn(city);

        cityService.updateCity(1L, new CityDto());

        //call verification
        verify(cityRepository, times(1)).findById(isA(Long.class));
        verify(cityRepository, times(1)).save(isA(City.class));
    }

    @Test
    void testUpdateCity_NotFound() {
        when(cityRepository.findById(isA(Long.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(CityNotFoundException.class, () -> cityService.updateCity(1L, new CityDto()));

        //call verification
        verify(cityRepository, times(1)).findById(isA(Long.class));
    }

    @Test
    void testGetCityByName_Found() {
        City city = new City();

        when(cityRepository.findByNameIgnoreCase(isA(String.class))).thenReturn(Optional.of(city));
        when(cityMapper.map(isA(City.class))).thenReturn(new CityDto());

        CityDto cityDto = cityService.getCityByName("name");

        //call verification
        verify(cityRepository, times(1)).findByNameIgnoreCase(isA(String.class));
        verify(cityMapper, times(1)).map(isA(City.class));

        //object verification
        assertNotNull(cityDto);
    }

    @Test
    void testGetCityByName_NotFound() {
        when(cityRepository.findByNameIgnoreCase(isA(String.class)))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(CityNotFoundException.class, () ->
                cityService.getCityByName("name"));

        //call verification
        verify(cityRepository, times(1)).findByNameIgnoreCase(isA(String.class));
    }

    @Test
    void testLoadCities() {
        when(cityCsvConverter.getObjectsFromCsv()).thenReturn(List.of(new CityCsvDto(), new CityCsvDto()));
        when(cityMapper.map(isA(CityCsvDto.class))).thenReturn(new City());
        when(cityRepository.saveAll(isA(List.class))).thenReturn(List.of(new City(), new City()));

        cityService.loadCities();

        //call verification
        verify(cityCsvConverter, times(1)).getObjectsFromCsv();
        verify(cityMapper, times(2)).map(isA(CityCsvDto.class));
        verify(cityRepository, times(1)).saveAll(isA(List.class));
    }

}
