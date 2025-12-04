package com.example.granja.patos.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PatoVendaDetalheDTOResponse(
        Long patoId,
        String patoNome,

        Long clienteId,
        String clienteNome,
        Boolean clienteDesconto,

        Long vendaId,
        BigDecimal valor,
        Date dataHora,

        Long vendedorId,
        String vendedorNome) 
{
	public static PatoVendaDetalheDTOResponse from(PatoVendaDetalheDTO p) {
        return new PatoVendaDetalheDTOResponse(
        		p.getPatoId(),
                p.getPatoNome(),
                p.getClienteId(),
                p.getClienteNome(),
                p.getClienteDesconto(),
                p.getVendaId(),
                p.getValor(),
                p.getDataHora(),
                p.getVendedorId(),
                p.getVendedorNome()
        );
    }
}