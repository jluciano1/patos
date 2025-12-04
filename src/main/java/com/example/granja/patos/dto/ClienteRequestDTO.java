package com.example.granja.patos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteRequestDTO {

    private String nome;
    private Boolean desconto;
    
}
