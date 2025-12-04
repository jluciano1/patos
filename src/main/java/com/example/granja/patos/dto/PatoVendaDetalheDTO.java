package com.example.granja.patos.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatoVendaDetalheDTO {

    private Long patoId;
    private String patoNome;

    private Long clienteId;
    private String clienteNome;
    private Boolean clienteDesconto;

    private Long vendaId;
    private BigDecimal valor;
    private Date dataHora;

    private Long vendedorId;
    private String vendedorNome;
}
