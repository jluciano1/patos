package com.example.granja.patos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.granja.patos.dto.PatoVendaDetalheDTO;
import com.example.granja.patos.entity.Pato;
import com.example.granja.patos.repository.PatoRepository;
import com.example.granja.patos.service.GranjaService;

@DataJpaTest
@ActiveProfiles("test")
class GranjaServiceTest {

    @Autowired
    private GranjaService service;

    @Test
    @DisplayName("Cadastro de novo pato")
    void cadastrarPatoTest() {
    	
    }
    
    @Test
    @DisplayName("Cadastro de novo cliente")
    void cadastrarClienteTest() {
    	
    }
	
    @Test
    @DisplayName("Cadastro de novo cliente")
    void cadastrarVendedorTest() {
    	
    }
    
    @Test
    @DisplayName("Cadastro de novo cliente")
    void cadastrarVendaTest() {
    	
    }
    
    @Test
    @DisplayName("Listagem de vendas")
    void listPatosVendidosTest() {
    	
    }
    
    @Test
    @DisplayName("Ranking de vendedores")
    void listVendedoresRankingTest() {
    	
    }
    
    @Test
    @DisplayName("Planilha")
    void gerarRelatorioVendaExcel() {
    	
    }
}