package com.senac.pizzademo.controller;

import com.senac.pizzademo.model.Ingrediente;
import com.senac.pizzademo.repository.IngredienteRepository;
import com.senac.pizzademo.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAllIngredientes() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        return new ResponseEntity<>(ingredientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> getIngredienteById(@PathVariable Long id) {
        Optional<Ingrediente> ingrediente = ingredienteRepository.findById(id);
        return ingrediente.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingrediente> createIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente novoIngrediente = ingredienteRepository.save(ingrediente);
        return new ResponseEntity<>(novoIngrediente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> updateIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingredienteAtualizado) {
        Optional<Ingrediente> ingredienteExistente = ingredienteRepository.findById(id);
        if (ingredienteExistente.isPresent()) {
            ingredienteAtualizado.setId(id);
            Ingrediente ingredienteSalvo = ingredienteRepository.save(ingredienteAtualizado);
            return new ResponseEntity<>(ingredienteSalvo, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngrediente(@PathVariable Long id) {
        if (ingredienteRepository.existsById(id)) {
            ingredienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para listar ingredientes de uma pizza específica
    @GetMapping("/pizza/{pizzaId}")
    public ResponseEntity<List<Ingrediente>> getIngredientesByPizzaId(@PathVariable Long pizzaId) {
        if (!pizzaRepository.existsById(pizzaId)) {
            return ResponseEntity.notFound().build();
        }
        List<Ingrediente> ingredientes = ingredienteRepository.findByPizzaId(pizzaId);
        return new ResponseEntity<>(ingredientes, HttpStatus.OK);
    }
}