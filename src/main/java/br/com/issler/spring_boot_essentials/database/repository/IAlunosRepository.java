package br.com.issler.spring_boot_essentials.database.repository;

import br.com.issler.spring_boot_essentials.database.model.AlunosEntity;
import br.com.issler.spring_boot_essentials.database.model.TreinosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// JPA Repo para paginação, se nao, utilizar CrudRepository
// com JPA Repo, nao é preciso utilizar @Repository
// O primeiro parâmetro é a classe da entidade, e o segundo é o tipo do ID da entidade
public interface IAlunosRepository extends JpaRepository<AlunosEntity, Integer> {
    Optional<AlunosEntity> findByEmail(String email);

    // por mais que na entidade esteja definido LAZY, nesse metodo sera carregado (como se fosse EAGER)
    @Query(value = "SELECT a FROM AlunosEntity a JOIN FETCH a.avaliacaoFisica WHERE a.id = :alunoId")
    Optional<AlunosEntity> findByIdFetch(Integer alunoId);
}
