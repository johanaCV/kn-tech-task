package com.kuehnenagel.city.mapper;

import com.kuehnenagel.city.entity.City;
import com.kuehnenagel.city.pojo.csv.CityCsvDto;
import com.kuehnenagel.city.pojo.dto.CityDto;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {

    City map(CityCsvDto cityCsvDto);

    CityDto map(City city);

}
