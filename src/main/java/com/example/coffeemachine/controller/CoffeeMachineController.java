package com.example.coffeemachine.controller;

import com.example.coffeemachine.entity.CoffeeMachine;
import com.example.coffeemachine.service.CoffeeMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coffee-machines")
@RequiredArgsConstructor
public class CoffeeMachineController {

    private final CoffeeMachineService coffeeMachineService;

    @PostMapping("/create")
    public ResponseEntity<Void> createCoffeeMachine(@RequestBody CoffeeMachine coffeeMachine) {
        coffeeMachineService.createCoffeeMachine(coffeeMachine);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CoffeeMachine>> getAllCoffeeMachines() {
        List<CoffeeMachine> coffeeMachines = coffeeMachineService.getAllCoffeeMachines();
        return new ResponseEntity<>(coffeeMachines, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffeeMachine(@PathVariable Long id) {
        coffeeMachineService.deleteCoffeeMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/turn-on")
    public ResponseEntity<Void> turnOnCoffeeMachine(@PathVariable Long id) {
        coffeeMachineService.turnOnCoffeeMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/turn-off")
    public ResponseEntity<Void> turnOffCoffeeMachine(@PathVariable Long id) {
        coffeeMachineService.turnOffCoffeeMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/pour-coffee")
    public ResponseEntity<Void> pourCoffee(@PathVariable Long id, @RequestParam Integer quantity) {
        coffeeMachineService.pourCoffee(id, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/add-coffee")
    public ResponseEntity<Void> addCoffee(@PathVariable Long id, @RequestParam Integer quantity) {
        coffeeMachineService.addCoffee(id, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
