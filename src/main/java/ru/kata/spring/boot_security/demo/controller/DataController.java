package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.MyUser;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {
    private final String firstPage = "redirect:/admin";
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DataController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/findOne")
    public MyUser findOne(@RequestParam(value = "id", required = true) Long id) {
        MyUser user = userRepository.findUserById(id);
        user.setRolesInString();
        return user;
    }

    @GetMapping("/findAll")
    public List<MyUser> findAll() {
        return userRepository.findAll();
    }



    @PostMapping()
    public ModelAndView createUser(@ModelAttribute("user") @Valid MyUser user, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(firstPage);
        if (bindingResult.hasErrors()) return mav;
        userService.saveUser(user);
        return mav;
    }

    @PatchMapping("/edit")
    public ModelAndView updateUserFromModal(MyUser user) {
        ModelAndView mav = new ModelAndView(firstPage);
        userService.update(user);
        return mav;
    }

    @DeleteMapping("/edit")
    public ModelAndView deleteUserFromModal(MyUser user) {
        ModelAndView mav = new ModelAndView(firstPage);
        userRepository.delete(user);
        return mav;
    }

}
