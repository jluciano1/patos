package com.example.granja.patos.dto;

import java.math.BigDecimal;

public record VendedorVendaDTOResponse(
        Long idVendedor,
        String nomeVendedor,
        BigDecimal valorTotal,
        Integer quantidadeVenda,
        Integer ranking) 
{
	public static VendedorVendaDTOResponse from(VendedorVendaDTO v) {
        return new VendedorVendaDTOResponse(
        		v.getIdVendedor(),
                v.getNomeVendedor(),
                v.getValorTotal(),
                v.getQuantidadeVenda(),
                v.getRanking()
        );
    }
}