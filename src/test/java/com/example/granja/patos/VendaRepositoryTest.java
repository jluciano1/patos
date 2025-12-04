package com.example.granja.patos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

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
class VendaRepositoryTest {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private PatoRepository patoRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Test
    @DisplayName("Buscar venda de um determinado pato")
    void findByIdPatoTest() {
    	Cliente c = new Cliente();
    	c.setNome("A");
    	c.setDesconto(false);
    	clienteRepository.saveAndFlush(c);
    	Pato p = new Pato();
    	p.setNome("B");
    	p.setStatus("D");
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
    	Optional<Venda> venda = vendaRepository.findByIdPato(p.getId());
    	Assert.isTrue(venda.isPresent(), "Venda não encontrada.");
    }
}