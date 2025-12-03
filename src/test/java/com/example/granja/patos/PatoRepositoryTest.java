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

@DataJpaTest
@ActiveProfiles("test")
class PatoRepositoryTest {

    @Autowired
    private PatoRepository patoRepository;

    @Test
    @DisplayName("Deve contar corretamente a quantidade de filhos de um pato")
    void countByMaeIdTest() {
    	Pato mae = inserirUmaMaeETresFilhos();

        long total = patoRepository.countByMaeId(mae.getId());

        assertThat(total).isEqualTo(3);
    }
    
    private Pato inserirUmaMaeETresFilhos() {
    	Pato mae = new Pato();
        mae.setNome("Pato Mãe");
        mae = patoRepository.saveAndFlush(mae);
        mae.setStatus("D");
        for (int i = 1; i <= 3; i++) {
            Pato filho = new Pato();
            filho.setNome("Filho " + i);
            filho.setMae(mae);
            filho.setStatus("D");
            patoRepository.saveAndFlush(filho);
        }
        return mae;
	}

	@Test
    @DisplayName("Deve contar corretamente a quantidade de filhos de um pato")
    void findByStatusTest() {
        inserirUmaMaeETresFilhos();
        List<PatoVendaDetalheDTO> lista = patoRepository.findByStatus("D");
        assertThat(lista.size()).isEqualTo(4);
    }
	
	@Test
    @DisplayName("Deve contar corretamente a quantidade de patos mãe")
    void findByMaeIsNullTest() {
        inserirUmaMaeETresFilhos();
        List<Pato> lista = patoRepository.findByMaeIsNull();
        assertThat(lista.size()).isEqualTo(1);
    }
	
	@Test
    @DisplayName("Deve contar corretamente a quantidade de patos filhos de um pato")
    void findByMaeIdTest() {
        inserirUmaMaeETresFilhos();
        List<Pato> listaMae = patoRepository.findByMaeIsNull();
        List<Pato> lista = patoRepository.findByMaeId(listaMae.get(0).getId());
        assertThat(lista.size()).isEqualTo(3);
    }
}