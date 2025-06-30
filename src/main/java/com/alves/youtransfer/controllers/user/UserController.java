package com.alves.youtransfer.controllers.user;

import com.alves.youtransfer.models.user.User;
import com.alves.youtransfer.services.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/email")
    public User getByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PatchMapping("/name")
    public void changeName(@RequestBody User user) {
        userService.changeName(user.getId(), user.getName());
    }

    @PatchMapping("/password")
    public void changePassword(@RequestBody User user) {
        userService.changePassword(user.getId(), user.getPassword());
    }

    @DeleteMapping
    public void delete(@RequestBody User user) {
        Long id = user.getId();
        userService.delete(id);
    }
}

