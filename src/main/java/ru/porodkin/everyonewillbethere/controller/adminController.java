package ru.porodkin.everyonewillbethere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.porodkin.everyonewillbethere.entity.Voyage;
import ru.porodkin.everyonewillbethere.repository.BusStationRepository;
import ru.porodkin.everyonewillbethere.repository.VoyageRepository;
import ru.porodkin.everyonewillbethere.service.VoyageService;

import java.time.DayOfWeek;

@Controller
@RequestMapping("/admin")
public class adminController {

    private final VoyageRepository voyageRepository;
    private final BusStationRepository busStationRepository;
    private final VoyageService service;

    public adminController(VoyageRepository voyageRepository, BusStationRepository busStationRepository, VoyageService service) {
        this.voyageRepository = voyageRepository;
        this.busStationRepository = busStationRepository;
        this.service = service;
    }

    @GetMapping
    public String viewAdminConsole(Model model) {
        model.addAttribute("dayOfWeek", DayOfWeek.values());
        model.addAttribute("busStations", busStationRepository.findAll());
        model.addAttribute("voyages", voyageRepository.findAll());
        return "admin_console";
    }

    @GetMapping("{voyage}")
    public String editVoyage(@PathVariable Voyage voyage, Model model) {
        model.addAttribute("dayOfWeek", DayOfWeek.values());
        model.addAttribute("busStations", busStationRepository.findAll());
        model.addAttribute("voyage", voyage);

        return "edit_voyage";
    }

    @PostMapping("{voyage}")
    public String putVoyage(@PathVariable("voyage") Voyage voyageFromDb, @RequestBody String voyage) {
        System.out.println("******************************************");

        service.updateEntity(voyageFromDb, voyage);
//        BeanUtils.copyProperties(voyage, voyageFromDb, "id");
//
//        voyageRepository.save(voyageFromDb);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{voyage}")
    public String deleteVoyage(@PathVariable Long voyage) {

        service.deleteEntity(voyage);

        return "redirect:/admin";
    }

    @PostMapping
    public String saveVoyage(@RequestBody String voyage) {

        service.saveEntity(voyage);

//        String[] split = voyage.split("\n");
//        Map<String, String> values = new HashMap<>();
//
//        Voyage voy = new Voyage();
//        Set<DayOfWeek> dayOfWeeks = new HashSet<>();
//
//        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> {
//            if (keyVal[0].equals("daysToDeparts")) {
//                String s = keyVal[1];
//                String replace = s.replace("\r", "");
//                dayOfWeeks.add(DayOfWeek.of(Integer.parseInt(replace)));
//            } else {
//                values.put(keyVal[0], keyVal[1]);
//            }
//        });
//
//        voy.setPrice(Long.valueOf(strip(values, "price")));
//        voy.setTravelTime(LocalTime.parse(strip(values, "travelTime")));
//        voy.setDayOfDeparture(dayOfWeeks);
//        voy.setTravelTimeDeparture(strip(values, "travelTimeDeparture"));
//        voy.setTravelTimeDestination(strip(values, "travelTimeDestination"));
//        voy.setPointOfDeparture(busStationRepository.getOne(Long.valueOf(strip(values, "pointOfDeparture"))));
//        voy.setPointOfDestination(busStationRepository.getOne(Long.valueOf(strip(values, "pointOfDestination"))));
//
//        voyageRepository.save(voy);

        return "redirect:/admin";
    }
}
