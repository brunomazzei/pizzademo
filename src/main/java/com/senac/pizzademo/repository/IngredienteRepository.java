package com.senac.pizzademo.repository;

import com.senac.pizzademo.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui, se necessário.
    List<Ingrediente> findByPizzaId(Long pizzaId);
}