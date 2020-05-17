package ru.porodkin.everyonewillbethere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.entity.Voyage;
import ru.porodkin.everyonewillbethere.repository.BusStationRepository;
import ru.porodkin.everyonewillbethere.repository.CityRepository;
import ru.porodkin.everyonewillbethere.service.BusStationService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/station")
public class BusStationController {

    private final BusStationRepository busStationRepository;
    private final CityRepository cityRepository;
    private final BusStationService service;

    public BusStationController(BusStationRepository busStationRepository, CityRepository cityRepository, BusStationService service) {
        this.busStationRepository = busStationRepository;
        this.cityRepository = cityRepository;
        this.service = service;
    }

    @GetMapping
    public String viewAddStation(Model model) {
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("stations", busStationRepository.findAll());

        return "add_bus_station";
    }

    @GetMapping("{id}")
    public String viewStationInfo(@PathVariable Long id, Model model){
        BusStation one = busStationRepository.getOne(id);
        model.addAttribute("busStation", one);

        return "bus_description";
    }

    @GetMapping("/edit/{station}")
    public String editStation(@PathVariable BusStation station, Model model) {
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("station", station);

        return "edit_station";
    }

    @PostMapping("/edit/{station}")
    public String putStation(@PathVariable("station") BusStation stationFromDb, @RequestBody String station) {
        service.updateEntity(stationFromDb, station);

        return "redirect:/station";
    }

    @GetMapping("/delete/{station}")
    public String deleteStation(@PathVariable Long station) {

        service.deleteEntity(station);

        return "redirect:/station";
    }

    @PostMapping
    public String addStation(@RequestBody String station) {

        service.saveEntity(station);

        return "redirect:/station";
    }
}
