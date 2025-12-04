package com.example.granja.patos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.granja.patos.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
	
    Optional<Venda> findByIdPato(Long idPato);
}

