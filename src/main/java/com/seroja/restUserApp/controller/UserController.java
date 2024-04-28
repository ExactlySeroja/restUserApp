package com.seroja.restUserApp.controller;

import com.seroja.restUserApp.dto.UserDto;
import com.seroja.restUserApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService service;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(service.getDto(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public List<UserDto> get(@RequestParam(required = false) LocalDate maxDate, @RequestParam(required = false) LocalDate minDate) {
        if (maxDate != null && minDate != null) {
            return service.getInRange(maxDate, minDate);
        }
        return service.listAll();
    }

    @PostMapping("/users")
    public ResponseEntity<?> add(@RequestBody @Valid UserDto UserDto) {
        try {
            return new ResponseEntity<>(service.save(UserDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid UserDto userDto, @PathVariable Integer id) {
        service.update(userDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateFew(@RequestBody @Valid UserDto userDto, @PathVariable Integer id) {
        service.update(userDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }


}
