package com.example.granja.patos.service;

import java.util.Date;
import java.util.List;

import com.example.granja.patos.dto.ClienteRequestDTO;
import com.example.granja.patos.dto.ClienteResponseDTO;
import com.example.granja.patos.dto.PatoRequestDTO;
import com.example.granja.patos.dto.PatoResponseDTO;
import com.example.granja.patos.dto.PatoVendaDetalheDTOResponse;
import com.example.granja.patos.dto.VendaRequestDTO;
import com.example.granja.patos.dto.VendaResponseDTO;
import com.example.granja.patos.dto.VendedorRequestDTO;
import com.example.granja.patos.dto.VendedorResponseDTO;
import com.example.granja.patos.dto.VendedorVendaDTOResponse;
import com.example.granja.patos.exceptions.GranjaHttpException;

import jakarta.validation.Valid;

public interface GranjaServiceGeneric {

     public PatoResponseDTO cadastrarPato(PatoRequestDTO pato) throws GranjaHttpException;
     public ClienteResponseDTO cadastrarCliente(@Valid ClienteRequestDTO cliente);
     public VendedorResponseDTO cadastrarVendedor(@Valid VendedorRequestDTO vendedor);
     public VendaResponseDTO cadastrarVenda(@Valid VendaRequestDTO venda);
     public List<PatoVendaDetalheDTOResponse> listPatosVendidos();
     public List<VendedorVendaDTOResponse> listVendedoresRanking(Date inicio,
             Date fim,
             String status,
             Integer ranking,
             String ordenarPor);
     
}
