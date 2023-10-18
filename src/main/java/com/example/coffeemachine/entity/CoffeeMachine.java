package com.example.coffeemachine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coffee_machine")
@Getter
@Setter
@NoArgsConstructor
public class CoffeeMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "coffee_level")
    private Integer coffeeLevel;

    @Column(name = "is_on")
    private Boolean isOn;
}
