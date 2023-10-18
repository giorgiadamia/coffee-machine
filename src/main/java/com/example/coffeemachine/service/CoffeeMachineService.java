package com.example.coffeemachine.service;

import com.example.coffeemachine.controller.dto.CoffeeMachineDto;
import com.example.coffeemachine.controller.dto.CoffeeMachineResponseDto;
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

    public CoffeeMachineResponseDto createCoffeeMachine(CoffeeMachineDto coffeeMachineDto) {
        if (coffeeMachineDto.getName() == null || coffeeMachineDto.getName().equals("")) {
            throw new IllegalArgumentException("Name is required");
        }

        Optional<CoffeeMachine> coffeeMachineOptional = coffeeMachineRepository.findByName(coffeeMachineDto.getName());

        if (coffeeMachineOptional.isPresent()) {
            throw new IllegalArgumentException("Coffee machine with this name already exists");
        }

        CoffeeMachine coffeeMachine = mapToEntity(coffeeMachineDto);
        coffeeMachineRepository.save(coffeeMachine);
        return mapToResponseDto(coffeeMachine);
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

    public List<CoffeeMachineResponseDto> getAllCoffeeMachines() {
        List<CoffeeMachine> coffeeMachines = coffeeMachineRepository.findAll();
        return coffeeMachines.stream().map(this::mapToResponseDto).collect(Collectors.toList());
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

    private CoffeeMachineResponseDto mapToResponseDto(CoffeeMachine coffeeMachine) {
        CoffeeMachineResponseDto coffeeMachineResponseDto = new CoffeeMachineResponseDto();
        coffeeMachineResponseDto.setId(coffeeMachine.getId());
        coffeeMachineResponseDto.setName(coffeeMachine.getName());
        coffeeMachineResponseDto.setIsOn(coffeeMachine.getIsOn());
        coffeeMachineResponseDto.setCoffeeLevel(coffeeMachine.getCoffeeLevel());
        coffeeMachineResponseDto.setCapacity(coffeeMachine.getCapacity());

        return coffeeMachineResponseDto;
    }

    private CoffeeMachine mapToEntity(CoffeeMachineDto coffeeMachineDto) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.setName(coffeeMachineDto.getName());

        if (coffeeMachineDto.getIsOn() == null) {
            coffeeMachine.setIsOn(false);
        } else {
            coffeeMachine.setIsOn(coffeeMachineDto.getIsOn());
        }

        if (coffeeMachineDto.getCoffeeLevel() == null) {
            coffeeMachine.setCoffeeLevel(0);
        } else {
            coffeeMachine.setCoffeeLevel(coffeeMachineDto.getCoffeeLevel());
        }

        if (coffeeMachineDto.getCapacity() == null) {
            coffeeMachine.setCapacity(10);
        } else {
            coffeeMachine.setCapacity(coffeeMachineDto.getCapacity());
        }

        return coffeeMachine;
    }
}
