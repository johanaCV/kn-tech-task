package com.kuehnenagel.city.controller;

import com.kuehnenagel.city.pojo.dto.CityDto;
import com.kuehnenagel.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/cities")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = {""})
    public ResponseEntity<Page<CityDto>> getCitiesDefault() {
        return new ResponseEntity<>(cityService.getCities(), HttpStatus.OK);
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<CityDto>> getCitiesPaginated(@RequestParam Integer page,
                                                            @RequestParam Integer size) {
        return new ResponseEntity<>(cityService.getCitiesPaginated(page, size),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCityPartially(@PathVariable @NonNull Long id,
                                                    @RequestBody @NonNull CityDto cityDto) {
        cityService.updateCity(id, cityDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<CityDto> getCityByName(@RequestParam @NonNull String name) {
        return new ResponseEntity<>(cityService.getCityByName(name),
                HttpStatus.OK);
    }

}
