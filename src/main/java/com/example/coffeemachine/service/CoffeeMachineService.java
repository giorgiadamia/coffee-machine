package com.example.coffeemachine.service;

import com.example.coffeemachine.controller.dto.CoffeeMachineDto;
import com.example.coffeemachine.entity.CoffeeMachine;
import com.example.coffeemachine.repository.CoffeeMachineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoffeeMachineService {

    private final CoffeeMachineRepository coffeeMachineRepository;

    public CoffeeMachineDto createCoffeeMachine(CoffeeMachineDto coffeeMachineDto) {
        if (coffeeMachineDto.getName() == null || coffeeMachineDto.getName().equals("")) {
            throw new IllegalArgumentException("Name is required");
        }

        Optional<CoffeeMachine> coffeeMachineOptional = coffeeMachineRepository.findByName(coffeeMachineDto.getName());

        if (coffeeMachineOptional.isPresent()) {
            throw new IllegalArgumentException("Coffee machine with this name already exists");
        }

        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.setName(coffeeMachineDto.getName());

        if (coffeeMachineDto.getIsOn() == null) {
            coffeeMachineDto.setIsOn(false);
            coffeeMachine.setIsOn(false);
        }

        if (coffeeMachineDto.getCoffeeLevel() == null) {
            coffeeMachineDto.setCoffeeLevel(0);
            coffeeMachine.setCoffeeLevel(0);
        }

        if (coffeeMachineDto.getCapacity() == null) {
            coffeeMachineDto.setCapacity(10);
            coffeeMachine.setCapacity(10);
        }

        coffeeMachine.setIsOn(coffeeMachineDto.getIsOn());
        coffeeMachine.setCoffeeLevel(coffeeMachineDto.getCoffeeLevel());
        coffeeMachine.setCapacity(coffeeMachineDto.getCapacity());

        coffeeMachineRepository.save(coffeeMachine);
        return coffeeMachineDto;
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

    public List<CoffeeMachineDto> getAllCoffeeMachines() {
        List<CoffeeMachine> coffeeMachines = coffeeMachineRepository.findAll();

        return coffeeMachines.stream().map(coffeeMachine -> {
            CoffeeMachineDto coffeeMachineDto = new CoffeeMachineDto();
            coffeeMachineDto.setName(coffeeMachine.getName());
            coffeeMachineDto.setIsOn(coffeeMachine.getIsOn());
            coffeeMachineDto.setCapacity(coffeeMachine.getCapacity());
            coffeeMachineDto.setCoffeeLevel(coffeeMachine.getCoffeeLevel());
            return coffeeMachineDto;
        }).collect(Collectors.toList());
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
