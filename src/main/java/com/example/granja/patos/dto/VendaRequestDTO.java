package com.example.granja.patos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendaRequestDTO {

	private Long idCliente;
    private Long idVendedor;
    private Long idPato;
    
}
