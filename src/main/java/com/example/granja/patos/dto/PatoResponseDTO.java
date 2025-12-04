package com.example.granja.patos.dto;

import com.example.granja.patos.entity.Pato;

public record PatoResponseDTO(Long id, String nome, Long idMae, String status) {

    public static PatoResponseDTO from(Pato p) {
        return new PatoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getMae() != null ? p.getMae().getId() : null,
        		p.getStatus()
        );
    }
}
