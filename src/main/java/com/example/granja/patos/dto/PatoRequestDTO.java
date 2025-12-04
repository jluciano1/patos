package com.example.granja.patos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatoRequestDTO {

    private Long maeId;
    private String nome;
    private String status;
    
}
