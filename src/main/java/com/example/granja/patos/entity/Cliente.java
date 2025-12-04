package com.example.granja.patos.entity;

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
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq_gen")
    @SequenceGenerator(
        name = "cliente_seq_gen",
        sequenceName = "cliente_SEQ",
        allocationSize = 1
    )
    private Long id;

    private String nome;
    
    private Boolean desconto;
}
