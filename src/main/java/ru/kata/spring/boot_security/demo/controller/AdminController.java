package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.MyUser;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@Validated
public class AdminController {
    private final String firstPage = "redirect:/admin";
    private final UserService usServ;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserService usServ, RoleRepository roleRepository, UserRepository userRepository) {
        this.usServ = usServ;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String user(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        model.addAttribute("user", userRepository.findByUsername(username));
        return "admin-user";
    }

    @GetMapping()
    public String users(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        model.addAttribute("users", usServ.allUsers());
        model.addAttribute("user", userRepository.findByUsername(username));
        model.addAttribute("newuser", new MyUser());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid MyUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "new";
        usServ.saveUser(user);
        return firstPage;
    }

    @PatchMapping("/edit")
    public String updateUserFromModal(MyUser user) {
        usServ.update(user);
        return firstPage;
    }

    @DeleteMapping("/edit")
    public String deleteUserFromModal(MyUser user) {
        userRepository.delete(user);
        return firstPage;
    }

}
