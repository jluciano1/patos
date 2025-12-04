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
@Table(name = "vendedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendedor {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendedor_seq_gen")
    @SequenceGenerator(
        name = "vendedor_seq_gen",
        sequenceName = "vendedor_SEQ",
        allocationSize = 1
    )
    private Long id;

    private String nome;
    private Long cpf;
    private String matricula;
}
