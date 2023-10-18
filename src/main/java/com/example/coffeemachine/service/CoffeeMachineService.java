package com.example.coffeemachine.service;

import com.example.coffeemachine.entity.CoffeeMachine;
import com.example.coffeemachine.repository.CoffeeMachineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (coffeeMachine.getCoffeeLevel() == null) {
            coffeeMachine.setCoffeeLevel(0);
        }

        if (coffeeMachine.getCapacity() == null) {
            coffeeMachine.setCapacity(10);
        }

        Optional<CoffeeMachine> coffeeMachineOptional = coffeeMachineRepository.findByName(coffeeMachine.getName());

        if (coffeeMachineOptional.isPresent()) {
            throw new IllegalArgumentException("Coffee machine with this name already exists");
        }

        coffeeMachineRepository.save(coffeeMachine);
    }

    public void turnOnCoffeeMachine(Long id) {
        CoffeeMachine coffeeMachine = getCoffeeMachine(id);

        if (coffeeMachine.getIsOn().equals(true)) {
            throw new IllegalArgumentException("Coffee machine is already turned on");
        }

        coffeeMachine.setIsOn(true);
        coffeeMachineRepository.save(coffeeMachine);
    }

    public void turnOffCoffeeMachine(Long id) {
        CoffeeMachine coffeeMachine = getCoffeeMachine(id);

        if (coffeeMachine.getIsOn().equals(false)) {
            throw new IllegalArgumentException("Coffee machine is already turned off");
        }

        coffeeMachine.setIsOn(false);
        coffeeMachineRepository.save(coffeeMachine);
    }

    public List<CoffeeMachine> getAllCoffeeMachines() {
        return coffeeMachineRepository.findAll();
    }

    public void pourCoffee(Long id, Integer quantity) {
        CoffeeMachine coffeeMachine = getCoffeeMachine(id);

        if (coffeeMachine.getIsOn().equals(false)) {
            throw new IllegalArgumentException("Coffee machine is turned off");
        }

        if (quantity > coffeeMachine.getCoffeeLevel()) {
            throw new IllegalArgumentException("There is not enough coffee in the coffee machine");
        }

        coffeeMachine.setCoffeeLevel(coffeeMachine.getCoffeeLevel() - quantity);
        coffeeMachineRepository.save(coffeeMachine);
    }

    public void addCoffee(Long id, Integer quantity) {
        CoffeeMachine coffeeMachine = getCoffeeMachine(id);

        if ((quantity + coffeeMachine.getCoffeeLevel()) > coffeeMachine.getCapacity()) {
            throw new IllegalArgumentException("The coffee machine can't hold that much coffee");
        }

        coffeeMachine.setCoffeeLevel(quantity + coffeeMachine.getCoffeeLevel());
        coffeeMachineRepository.save(coffeeMachine);
    }

    public void deleteCoffeeMachine(Long id) {
        CoffeeMachine coffeeMachine = getCoffeeMachine(id);
        coffeeMachineRepository.delete(coffeeMachine);
    }

    private CoffeeMachine getCoffeeMachine(Long id) {
        Optional<CoffeeMachine> coffeeMachineOptional = coffeeMachineRepository.findById(id);

        if (coffeeMachineOptional.isEmpty()) {
            throw new EntityNotFoundException("Coffee machine with such id does not exist");
        }
        return coffeeMachineOptional.get();
    }
}
