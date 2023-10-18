package com.example.coffeemachine.controller;

import com.example.coffeemachine.entity.CoffeeMachine;
import com.example.coffeemachine.service.CoffeeMachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coffee-machines")
@RequiredArgsConstructor
@Tag(name = "Coffee Machine Controller", description = "Coffee Machine API")
public class CoffeeMachineController {

    private final CoffeeMachineService coffeeMachineService;

    @PostMapping("/create")
    @Operation(summary = "Create a coffee machine")
    public ResponseEntity<Void> createCoffeeMachine(@RequestBody CoffeeMachine coffeeMachine) {
        coffeeMachineService.createCoffeeMachine(coffeeMachine);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Display all coffee machines")
    public ResponseEntity<List<CoffeeMachine>> getAllCoffeeMachines() {
        List<CoffeeMachine> coffeeMachines = coffeeMachineService.getAllCoffeeMachines();
        return new ResponseEntity<>(coffeeMachines, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a coffee machine")
    public ResponseEntity<Void> deleteCoffeeMachine(@PathVariable Long id) {
        coffeeMachineService.deleteCoffeeMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/turn-on")
    @Operation(summary = "Turn on a coffee machine")
    public ResponseEntity<Void> turnOnCoffeeMachine(@PathVariable Long id) {
        coffeeMachineService.turnOnCoffeeMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/turn-off")
    @Operation(summary = "Turn off a coffee machine")
    public ResponseEntity<Void> turnOffCoffeeMachine(@PathVariable Long id) {
        coffeeMachineService.turnOffCoffeeMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/pour-coffee")
    @Operation(summary = "Pour coffee from a coffee machine")
    public ResponseEntity<Void> pourCoffee(@PathVariable Long id, @RequestParam Integer quantity) {
        coffeeMachineService.pourCoffee(id, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/add-coffee")
    @Operation(summary = "Add coffee to a coffee machine")
    public ResponseEntity<Void> addCoffee(@PathVariable Long id, @RequestParam Integer quantity) {
        coffeeMachineService.addCoffee(id, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
