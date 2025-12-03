package com.example.granja.patos;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import com.example.granja.patos.dto.VendedorVendaDTO;
import com.example.granja.patos.entity.Cliente;
import com.example.granja.patos.entity.Pato;
import com.example.granja.patos.entity.Venda;
import com.example.granja.patos.entity.Vendedor;
import com.example.granja.patos.repository.ClienteRepository;
import com.example.granja.patos.repository.PatoRepository;
import com.example.granja.patos.repository.VendaRepository;
import com.example.granja.patos.repository.VendedorRepository;

@DataJpaTest
@ActiveProfiles("test")
class VendedorRepositoryTest {

	@Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private PatoRepository patoRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Test
    @DisplayName("Buscar lista de venda por status e período")
    void findByStatusAndPeriodoTest() {
    	Cliente c = new Cliente();
    	c.setNome("A");
    	c.setDesconto(false);
    	clienteRepository.saveAndFlush(c);
    	Pato p = new Pato();
    	p.setNome("B");
    	p.setStatus("V");
    	patoRepository.saveAndFlush(p);
    	Vendedor v = new Vendedor();
    	v.setCpf(Long.valueOf(1));
    	v.setMatricula("A1");
    	v.setNome("C");
    	vendedorRepository.saveAndFlush(v);
    	Venda vend = new Venda();
    	vend.setDataHora(new Date());
    	vend.setIdCliente(c.getId());
    	vend.setIdPato(p.getId());
    	vend.setIdVendedor(v.getId());
    	vend.setValor(BigDecimal.TEN);
    	vendaRepository.save(vend);
    	LocalDateTime doisDiasAtras = LocalDateTime.now().minusDays(2);
    	Date date = Date.from(doisDiasAtras.atZone(ZoneId.systemDefault()).toInstant());

    	List<VendedorVendaDTO> lista = vendedorRepository.findByStatusAndPeriodo(date, new Date(), "V");
    	assertThat(lista.size()).isEqualTo(1);
    }
	
	
}