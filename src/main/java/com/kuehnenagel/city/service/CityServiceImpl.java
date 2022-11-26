package com.kuehnenagel.city.service;

import com.kuehnenagel.city.entity.City;
import com.kuehnenagel.city.exception.CityNotFoundException;
import com.kuehnenagel.city.mapper.CityMapper;
import com.kuehnenagel.city.pojo.dto.CityDto;
import com.kuehnenagel.city.repository.CityRepository;
import com.kuehnenagel.city.util.csv.CityCsvParser;
import com.kuehnenagel.city.util.photo.PhotoBlobStrategy;
import com.kuehnenagel.city.util.photo.PhotoConverterStrategy;
import com.kuehnenagel.city.util.photo.PhotoUrlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;
    private CityCsvParser cityCsvConverter;
    private Integer defaultPage;
    private Integer defaultPageSize;
    private String selectedPhotoStrategy;
    private PhotoConverterStrategy photoConverter;
    CityMapper cityMapper;

    private final static String PHOTO_URL_STRATEGY = "url";
    private final static String PHOTO_BLOB_STRATEGY = "blob";

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    public CityServiceImpl(CityRepository cityRepository,
                           CityMapper cityMapper,
                           CityCsvParser cityCsvConverter,
                           @Value("${pagination.page.default:1}") Integer defaultPage,
                           @Value("${pagination.size.default:5}") Integer defaultPageSize,
                           @Value("${photo.convert}") String selectedPhotoStrategy) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.cityCsvConverter = cityCsvConverter;
        this.defaultPage = defaultPage;
        this.defaultPageSize = defaultPageSize;
        this.selectedPhotoStrategy = selectedPhotoStrategy;

        LOGGER.info("Handling photos as {} ", selectedPhotoStrategy);
        if (this.selectedPhotoStrategy.equals(PHOTO_BLOB_STRATEGY)) {
            this.photoConverter = new PhotoBlobStrategy();
        } else if (this.selectedPhotoStrategy.equals(PHOTO_URL_STRATEGY)) {
            this.photoConverter = new PhotoUrlStrategy();
        }
    }

    @Override
    public Page<CityDto> getCitiesPaginated(Integer page, Integer size) {
        int computedPage = page > 0 ? page : defaultPage;
        return cityRepository.findAll(PageRequest.of(computedPage - 1, size))
                .map(cityMapper::map);
    }

    @Override
    public Page<CityDto> getCities() {
        // sent default values for pagination
        return getCitiesPaginated(defaultPage, defaultPageSize);
    }

    @Override
    public void updateCity(Long id, CityDto cityDto) {
        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException());

        //new values must not be null, otherwise the existing value will not be modified

        if (cityDto.getName() != null) {
            city.setName(cityDto.getName());
        }

        if (cityDto.getPhotoUri() != null) {
            if (selectedPhotoStrategy.equals(PHOTO_URL_STRATEGY)) {
                city.setPhotoUri((String) photoConverter.convert(cityDto.getPhotoUri()));
            } else if (selectedPhotoStrategy.equals(PHOTO_BLOB_STRATEGY)) {
                city.setPhoto((Byte[]) photoConverter.convert(cityDto.getPhotoUri()));
            }
        }

        cityRepository.save(city);
    }

    @Override
    public CityDto getCityByName(String name) {
        City city = cityRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->  new CityNotFoundException());
        return cityMapper.map(city);
    }

    @Override
    public CityDto getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() ->  new CityNotFoundException());
        return cityMapper.map(city);
    }

    @Override
    public void loadCities() {
        LOGGER.info("Loading cities...");
        List<City> cities = cityCsvConverter.getObjectsFromCsv()
                .stream().map(cityMapper::map).collect(Collectors.toList());

        switch (selectedPhotoStrategy) {
            case PHOTO_URL_STRATEGY:
                cities.forEach(city ->
                        city.setPhotoUri(
                                (String) photoConverter.convert(city.getPhotoUri())));
                break;
            case PHOTO_BLOB_STRATEGY:
                cities.forEach(city ->
                        city.setPhoto((Byte[]) photoConverter.convert(city.getPhotoUri())));
                break;
        }

        cityRepository.saveAll(cities);
        LOGGER.info("Cities loaded.");
    }
}
