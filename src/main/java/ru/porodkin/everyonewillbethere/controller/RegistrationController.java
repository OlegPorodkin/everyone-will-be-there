package ru.porodkin.everyonewillbethere.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.porodkin.everyonewillbethere.entity.Role;
import ru.porodkin.everyonewillbethere.entity.User;
import ru.porodkin.everyonewillbethere.repository.UserRepository;

import javax.validation.Validator;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, Validator validator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User is exist!");
            return "registration";
        }

        if (!validator.validate(user).isEmpty()) {
            if (!validator.validateProperty(user, "username").isEmpty()) {
                validator.validateProperty(user, "username")
                        .forEach(mess -> model.addAttribute("usernameMess", mess.getMessage()));
            }
            if (!validator.validateProperty(user, "password").isEmpty()) {
                validator.validateProperty(user, "password")
                        .forEach(mess -> model.addAttribute("passwordMess", mess.getMessage()));
            }
            if (!validator.validateProperty(user, "password2").isEmpty()) {
                validator.validateProperty(user, "password2")
                        .forEach(mess -> model.addAttribute("password2Mess", mess.getMessage()));
            }

            if (!validator.validateProperty(user, "email").isEmpty()) {
                validator.validateProperty(user, "email")
                        .forEach(mess -> model.addAttribute("emailMess", mess.getMessage()));
            }

            if (!validator.validateProperty(user, "address").isEmpty()) {
                validator.validateProperty(user, "address")
                        .forEach(mess -> model.addAttribute("addressMess", mess.getMessage()));
            }
            return "registration";
        }

        if (!user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("message", "Пароли не совпадают");
            return "registration";
        }

        User newUser = new User();
        BeanUtils.copyProperties(user, newUser, "id");

        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);

        return "redirect:/login";
    }
}
