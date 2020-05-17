package ru.porodkin.everyonewillbethere.controller;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.entity.City;
import ru.porodkin.everyonewillbethere.repository.CityRepository;
import ru.porodkin.everyonewillbethere.service.CityService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/city")
public class CityController {

    private final CityRepository cityRepository;
    private final CityService service;

    public CityController(CityRepository cityRepository, CityService service) {
        this.cityRepository = cityRepository;
        this.service = service;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("cities", cityRepository.findAll());

        return "add_city";
    }

    @PostMapping
    public String addCity(@RequestBody String value, Model model){

        if (service.saveEntity(value)){
            model.addAttribute("error", "Город уже присутсвует в базе!");
            return "add_city";
        }

        return "redirect:/city";
    }

    @GetMapping("/edit/{city}")
    public String editStation(@PathVariable City city, Model model) {
        model.addAttribute("city", city);

        return "edit_city";
    }

    @PostMapping("/edit/{city}")
    public String putStation(@PathVariable("city") City cityFromDb, @RequestBody String city) {
        service.updateEntity(cityFromDb, city);

        return "redirect:/city";
    }

    @GetMapping("/delete/{city}")
    public String deleteCity(@PathVariable Long city) {

        service.deleteEntity(city);

        return "redirect:/city";
    }
}
