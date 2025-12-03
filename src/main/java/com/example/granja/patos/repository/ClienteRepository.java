package com.example.granja.patos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.granja.patos.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> { }

