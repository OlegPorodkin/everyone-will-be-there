package ru.porodkin.everyonewillbethere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.porodkin.everyonewillbethere.entity.Role;
import ru.porodkin.everyonewillbethere.entity.User;
import ru.porodkin.everyonewillbethere.entity.Voyage;
import ru.porodkin.everyonewillbethere.repository.BusStationRepository;
import ru.porodkin.everyonewillbethere.repository.VoyageRepository;
import ru.porodkin.everyonewillbethere.service.UserService;
import ru.porodkin.everyonewillbethere.service.VoyageService;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final VoyageRepository voyageRepository;
    private final BusStationRepository busStationRepository;
    private final VoyageService service;
    private final UserService userService;

    public AdminController(VoyageRepository voyageRepository, BusStationRepository busStationRepository, VoyageService service, UserService userService) {
        this.voyageRepository = voyageRepository;
        this.busStationRepository = busStationRepository;
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/users")
    public String viewAllUser(Model model) {
        Map<String, Object> data = new HashMap<>();
        List<User> all = userService.findAll();

        data.put("users", all);
        data.put("roles", Role.values());

        model.addAttribute("allUsers", data);
        return "user_admin";
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
        service.updateEntity(voyageFromDb, voyage);

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

        return "redirect:/admin";
    }
}
