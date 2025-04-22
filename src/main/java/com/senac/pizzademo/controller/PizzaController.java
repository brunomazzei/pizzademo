package com.senac.pizzademo.controller;

import com.senac.pizzademo.model.Pizza;
import com.senac.pizzademo.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        List<Pizza> pizzas = pizzaRepository.findAll();
        return new ResponseEntity<>(pizzas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        return pizza.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza) {
        Pizza novaPizza = pizzaRepository.save(pizza);
        return new ResponseEntity<>(novaPizza, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id, @RequestBody Pizza pizzaAtualizada) {
        Optional<Pizza> pizzaExistente = pizzaRepository.findById(id);
        if (pizzaExistente.isPresent()) {
            pizzaAtualizada.setId(id); // Garante que o ID seja o mesmo
            Pizza pizzaSalva = pizzaRepository.save(pizzaAtualizada);
            return new ResponseEntity<>(pizzaSalva, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pizza> deletePizza(@PathVariable Long id) {
        Optional<Pizza> pizzaToDelete = pizzaRepository.findById(id);
        if (pizzaToDelete.isPresent()) {
        pizzaRepository.deleteById(id);
            return ResponseEntity.ok(pizzaToDelete.get());
         } else {
         return ResponseEntity.notFound().build();
     }
    }
}
