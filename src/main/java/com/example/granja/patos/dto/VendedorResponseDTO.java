package com.example.granja.patos.dto;

import com.example.granja.patos.entity.Vendedor;

public record VendedorResponseDTO(Long id, String nome, Long cpf, String matricula) {

    public static VendedorResponseDTO from(Vendedor c) {
        return new VendedorResponseDTO(
                c.getId(),
                c.getNome(),
                c.getCpf(),
                c.getMatricula()
        );
    }
}
