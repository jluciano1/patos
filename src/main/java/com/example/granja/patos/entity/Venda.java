package com.example.granja.patos.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venda {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq_gen")
    @SequenceGenerator(
        name = "venda_seq_gen",
        sequenceName = "venda_SEQ",
        allocationSize = 1
    )
    private Long id;

    private Long idCliente;
    private Long idVendedor;
    private Long idPato;
    private BigDecimal valor;
    private Date dataHora;
    
}
