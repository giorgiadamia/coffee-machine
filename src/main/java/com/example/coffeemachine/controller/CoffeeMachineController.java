package com.example.coffeemachine.controller;

import com.example.coffeemachine.entity.CoffeeMachine;
import com.example.coffeemachine.service.CoffeeMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/coffee-machines")
@RequiredArgsConstructor
@Validated
public class CoffeeMachineController {

    private final CoffeeMachineService coffeeMachineService;

    @PostMapping("/create")
    public ResponseEntity<Void> createCoffeeMachine(@RequestBody @Valid CoffeeMachine coffeeMachine) {
        coffeeMachineService.createCoffeeMachine(coffeeMachine);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
