package ru.porodkin.everyonewillbethere.controller;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.entity.City;
import ru.porodkin.everyonewillbethere.repository.CityRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/add-city")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public String index(){
        return "add_city";
    }

    @PostMapping
    public String addCity(@RequestBody String value, Model model){

        String[] split = value.split("\n");
        Map<String, String> values = new HashMap<>();

        City city = new City();

        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> values.put(keyVal[0], keyVal[1]));

        String cityName = strip(values, "cityName");

        boolean namePresent = cityRepository.findOne(Specification.where((root, query, cb) -> {
            return cb.and(cb.like(root.get("name"), cityName));
        })).isPresent();

        if (namePresent){
            model.addAttribute("error", "Город уже присутсвует в базе!");
            return "add_city";
        }

        city.setName(cityName);
        cityRepository.save(city);

        return "add_city";
    }

    private String strip(Map<String, String> values, String key) {
        return values.get(key).replace("\r", "");
    }
}
