package com.kuehnenagel.city.controller;

import com.kuehnenagel.city.pojo.dto.CityDto;
import com.kuehnenagel.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/v1.0/cities")
public class CityController {

    private CityService cityService;

    private static final String CITY_VIEW_FOLDER = "city/";

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = {""})
    public String getCitiesDefault(Model model) {
        Page<CityDto> citiesPage = cityService.getCities();
        return getCitiesPaginatedModel(model, citiesPage);
    }

    @GetMapping(params = {"page", "size"})
    public String getCitiesPaginated(Model model, @RequestParam Integer page,
                                     @RequestParam Integer size) {
        Page<CityDto> citiesPage = cityService.getCitiesPaginated(page, size);
        return getCitiesPaginatedModel(model, citiesPage);
    }

    @GetMapping("/edit/{id}")
    public String getEditCityView(Model model, @PathVariable @NonNull Long id) {
        CityDto city = cityService.getCityById(id);
        model.addAttribute("city", city);
        return CITY_VIEW_FOLDER + "city_edit";
    }

    @PostMapping("/{id}")
    public String updateCityPartially(Model model, RedirectAttributes redirectAttributes,
                                      @PathVariable @NonNull Long id,
                                      CityDto cityDto) {
        cityService.updateCity(id, cityDto);
        redirectAttributes
                .addFlashAttribute("success", "City edited successfully!");
        return "redirect:/v1.0/cities";
    }

    @GetMapping(params = {"name"})
    public String getCityByName(Model model, @RequestParam @NonNull String name) {
        CityDto city = cityService.getCityByName(name);
        model.addAttribute("city", city);
        return CITY_VIEW_FOLDER + "city_visualize";
    }

    private String getCitiesPaginatedModel(Model model, Page<CityDto> cityPage) {
        model.addAttribute("cityPage", cityPage);
        if (cityPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, cityPage.getTotalPages())
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return CITY_VIEW_FOLDER + "city_list";
    }

}
