package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public DataController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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


    @PostMapping("/user")
    public ResponseEntity<MyUser> newUser(@RequestBody MyUser user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<MyUser> updateUser(@RequestBody MyUser user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<MyUser> deleteUserById(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
