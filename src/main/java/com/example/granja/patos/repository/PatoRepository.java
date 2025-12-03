package com.example.granja.patos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.granja.patos.dto.PatoVendaDetalheDTO;
import com.example.granja.patos.entity.Pato;

public interface PatoRepository extends JpaRepository<Pato, Long> {
	
		@Query("""
	        SELECT new com.example.granja.patos.dto.PatoVendaDetalheDTO(
		        p.id,
		        p.nome,
		        c.id,
		        c.nome,
		        c.desconto,
		        v.id,
		        v.valor,
		        v.dataHora,
		        vend.id,
		        vend.nome
		    )
		    FROM Pato p
		    LEFT JOIN Venda v ON v.idPato = p.id
		    LEFT JOIN Cliente c ON c.id = v.idCliente
		    LEFT JOIN Vendedor vend ON vend.id = v.idVendedor
		    WHERE p.status = :status
		    ORDER BY v.id NULLS LAST
	    """)
	    List<PatoVendaDetalheDTO> findByStatus(@Param("status") String status);
		
		List<Pato> findByMaeIsNull();
	    List<Pato> findByMaeId(Long idMae);
	    Integer countByMaeId(Long maeId);
}

