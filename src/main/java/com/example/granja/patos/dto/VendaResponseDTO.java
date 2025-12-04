package com.example.granja.patos.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.example.granja.patos.entity.Venda;

public record VendaResponseDTO(Long id, Long idCliente, Long idVendedor, Long idPato, BigDecimal valor, Date dataHora) {

    public static VendaResponseDTO from(Venda c) {
        return new VendaResponseDTO(
                c.getId(),
                c.getIdCliente(),
                c.getIdVendedor(),
                c.getIdPato(),
                c.getValor(),
                c.getDataHora()
        );
    }
}
