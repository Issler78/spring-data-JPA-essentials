package br.com.issler.spring_boot_essentials.database.repository;

import br.com.issler.spring_boot_essentials.database.model.AlunosEntity;
import br.com.issler.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.issler.spring_boot_essentials.dto.AvaliacoesFisicasProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

// JPA Repo para paginação, se nao, utilizar CrudRepository
// com JPA Repo, nao é preciso utilizar @Repository
// O primeiro parâmetro é a classe da entidade, e o segundo é o tipo do ID da entidade
public interface IAvaliacoesFisicasRepository extends JpaRepository<AvaliacoesFisicasEntity, Integer> {
    @NativeQuery(value = """
        SELECT a.id idAluno,
                a.nome nomeAluno,
                af.id idAvaliacao,
                af.peso peso,
                af.altura altura,
                af.percentual_gordura_corporal percentualGorduraCorporal
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
    """)
    List<AvaliacoesFisicasProjection> getAllAvaliacoes();

    @NativeQuery(value = """
        SELECT a.id idAluno,
                a.nome nomeAluno,
                af.id idAvaliacao,
                af.peso peso,
                af.altura altura,
                af.percentual_gordura_corporal percentualGorduraCorporal
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
    """,
    countQuery = """
        SELECT COUNT(af.id)
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
    """)
    Page<AvaliacoesFisicasProjection> getAllAvaliacoesPaginate(Pageable pageable);
}
