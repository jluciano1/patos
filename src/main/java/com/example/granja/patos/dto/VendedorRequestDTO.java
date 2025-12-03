package com.example.granja.patos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendedorRequestDTO {

    private String nome;
    private Long cpf;
    private String matricula;
    
}
