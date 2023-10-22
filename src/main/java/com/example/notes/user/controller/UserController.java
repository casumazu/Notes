package com.example.notes.user.controller;

import com.example.notes.user.dto.UserDto;
import com.example.notes.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Получен запрос на создание пользователя с почтой {}", userDto.getEmail());
        return userService.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<UserDto> findAllUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                                      @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                      @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {

        return userService.findAllUsers(ids, PageRequest.of(from, size));
    }
}