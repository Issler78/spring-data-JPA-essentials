package br.com.issler.spring_boot_essentials.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alunos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AlunosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;

    // eager = carrega sempre
    // lazy = preguiçoso, carrega apenas se definirmos

    // PADRAO ONE TO ONE = EAGER
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_fisica_id")
    private AvaliacoesFisicasEntity avaliacaoFisica;


    // PADRAO ONE TO MANY = LAZY
    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY) // mapeado por "aluno" na classe TreinosEntity
    private Set<TreinosEntity> treinos = new HashSet<>(); // nao ordenado
}
