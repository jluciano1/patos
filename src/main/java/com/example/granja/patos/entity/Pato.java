package com.example.granja.patos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pato {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pato_seq_gen")
    @SequenceGenerator(
        name = "pato_seq_gen",
        sequenceName = "PATO_SEQ",
        allocationSize = 1
    )
    private Long id;

    private String nome;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mae_id")
    private Pato mae;
	
    private String status;
}
