package com.senac.pizzademo.repository;

import com.senac.pizzademo.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui, se necessário.
    // Por exemplo:
    // List<Pizza> findByNomeContainingIgnoreCase(String nome);
}