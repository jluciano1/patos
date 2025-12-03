package com.example.granja.patos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.granja.patos.dto.VendedorVendaDTO;
import com.example.granja.patos.entity.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
	
	@Query("""
		    SELECT new com.example.granja.patos.dto.VendedorVendaDTO(
		        vdr.id,
			    vdr.nome,
			    SUM(v.valor),
			    CAST(COUNT(v) as int),
			    0     
		    )
		    FROM Venda v
		    left JOIN Vendedor vdr ON vdr.id = v.idVendedor
		    left JOIN Pato p ON p.id = v.idPato
		    WHERE v.dataHora BETWEEN :inicio AND :fim
		      AND p.status = :statusPato
		    GROUP BY vdr.id, vdr.nome
		""")
	    List<VendedorVendaDTO> findByStatusAndPeriodo(
	            @Param("inicio") Date inicio,
	            @Param("fim") Date fim,
	            @Param("statusPato") String statusPato
	    );
	
}

