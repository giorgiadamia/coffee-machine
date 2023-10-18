package com.example.coffeemachine.service;

import com.example.coffeemachine.entity.CoffeeMachine;
import com.example.coffeemachine.repository.CoffeeMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoffeeMachineService {

    private final CoffeeMachineRepository coffeeMachineRepository;

    public void createCoffeeMachine(CoffeeMachine coffeeMachine) {
        if (coffeeMachine.getName() == null || coffeeMachine.getName().equals("")) {
            throw new IllegalArgumentException("Name is required");
        }

        if (coffeeMachine.getIsOn() == null) {
            coffeeMachine.setIsOn(false);
        }

        Optional<CoffeeMachine> coffeeMachineOptional = coffeeMachineRepository.findByName(coffeeMachine.getName());

        if (coffeeMachineOptional.isPresent()) {
            throw new IllegalArgumentException("Coffee machine with this name already exists");
        }

        coffeeMachineRepository.save(coffeeMachine);
    }
}
