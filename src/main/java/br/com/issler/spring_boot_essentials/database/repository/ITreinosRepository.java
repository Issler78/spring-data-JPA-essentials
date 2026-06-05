package br.com.issler.spring_boot_essentials.database.repository;

import br.com.issler.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.issler.spring_boot_essentials.database.model.TreinosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JPA Repo para paginação, se nao, utilizar CrudRepository
// com JPA Repo, nao é preciso utilizar @Repository
// O primeiro parâmetro é a classe da entidade, e o segundo é o tipo do ID da entidade
public interface ITreinosRepository extends JpaRepository<TreinosEntity, Integer> {

    Optional<TreinosEntity> findByNomeAndAlunoId(String nome, Integer alunoId);
}
