package com.example.granja.patos.dto;

import com.example.granja.patos.entity.Cliente;

public record ClienteResponseDTO(Long id, String nome, Boolean desconto) {

    public static ClienteResponseDTO from(Cliente c) {
        return new ClienteResponseDTO(
                c.getId(),
                c.getNome(),
                c.getDesconto()
        );
    }
}
