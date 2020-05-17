package ru.porodkin.everyonewillbethere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.repository.BusStationRepository;
import ru.porodkin.everyonewillbethere.repository.CityRepository;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/station")
public class BusStationController {

    private final BusStationRepository busStationRepository;
    private final CityRepository cityRepository;

    public BusStationController(BusStationRepository busStationRepository, CityRepository cityRepository) {
        this.busStationRepository = busStationRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public String viewAddStation(Model model) {
        model.addAttribute("cities", cityRepository.findAll());

        return "add_bus_station";
    }

    @PostMapping
    public String addStation(@RequestBody String station) {

        String[] split = station.split("\n");
        Map<String, String> values = new HashMap<>();

        BusStation busStation = new BusStation();

        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> values.put(keyVal[0], keyVal[1]));

        initBusStationDeparture(values, busStation);

        busStation.setCity(cityRepository.getOne(Long.valueOf(strip(values, "city"))));

        busStationRepository.save(busStation);

        return "redirect:/station";
    }

    @GetMapping("{id}")
    public String viewStationInfo(@PathVariable Long id, Model model){
        BusStation one = busStationRepository.getOne(id);
        model.addAttribute("busStation", one);

        return "bus_description";
    }

    private void initBusStationDeparture(Map<String, String> values, BusStation pointOfDeparture) {
        pointOfDeparture.setName(values.get("name"));
        pointOfDeparture.setAddress(values.get("address"));
        pointOfDeparture.setPhone(values.get("phone"));
        pointOfDeparture.setStartTime(LocalTime.parse(strip(values, "startTime")));
        pointOfDeparture.setEndTime(LocalTime.parse(strip(values, "endTime")));
    }

    private String strip(Map<String, String> values, String key) {
        return values.get(key).replace("\r", "");
    }
}
