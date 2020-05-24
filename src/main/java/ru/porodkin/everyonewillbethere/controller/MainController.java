package ru.porodkin.everyonewillbethere.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.porodkin.everyonewillbethere.entity.User;
import ru.porodkin.everyonewillbethere.repository.VoyageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {

    private final VoyageRepository voyageRepository;

    public MainController(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {

        if (user != null){
            List<String> roleName = user.getRoles().stream().map(Enum::name).collect(Collectors.toList());
            model.addAttribute("roleName", roleName);
        }

        model.addAttribute("profile", user);
        return "index";
    }
}
