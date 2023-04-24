package com.github.kalininaleksandrv.makejpagreateagain.controller;

import com.github.kalininaleksandrv.makejpagreateagain.model.Manager;
import com.github.kalininaleksandrv.makejpagreateagain.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Slf4j
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping(path = "manager")
    public ResponseEntity<Manager> newManager(@RequestBody Manager manager) {
        return new ResponseEntity<>(managerService.saveManager(manager), HttpStatus.OK);
    }

    @GetMapping(path = "manager/{id}")
    public ResponseEntity<Manager> managerById(@PathVariable Integer id) {
        return managerService.findById(id).map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }
}
