package com.example.coffeemachine.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeMachineResponseDto {
    private Long id;
    private String name;
    private Integer capacity;
    private Integer coffeeLevel;
    private Boolean isOn;
}
