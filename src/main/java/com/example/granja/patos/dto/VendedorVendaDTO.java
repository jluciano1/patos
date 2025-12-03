package com.example.granja.patos.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendedorVendaDTO {

    private Long idVendedor;
    private String nomeVendedor;
    private BigDecimal valorTotal;
    private Integer quantidadeVenda;
    private Integer ranking;
}
