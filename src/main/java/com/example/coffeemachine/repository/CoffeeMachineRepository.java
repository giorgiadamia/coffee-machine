package com.example.coffeemachine.repository;

import com.example.coffeemachine.entity.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {

    Optional<CoffeeMachine> findByName(String name);
}
