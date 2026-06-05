package br.com.issler.spring_boot_essentials.database.repository;

import br.com.issler.spring_boot_essentials.database.model.ExerciciosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JPA Repo para paginação, se nao, utilizar CrudRepository
// com JPA Repo, nao é preciso utilizar @Repository
// O primeiro parâmetro é a classe da entidade, e o segundo é o tipo do ID da entidade
public interface IExerciciosRepository extends JpaRepository<ExerciciosEntity, Integer> {
    // query method
    List<ExerciciosEntity> findAllByGrupoMuscular(String grupoMuscular);

    // query com JPQL = linguagem de consulta orientada a objetos, e não SQL
    @Query(value = """
        SELECT e
            FROM ExerciciosEntity e
            WHERE UPPER(e.grupoMuscular) = UPPER(:grupoMuscular)
    """)
    List<ExerciciosEntity> findAllByGrupoMuscularJPQL(@Param("grupoMuscular") String grupoMuscular);



    // query com query nativa, ou seja, utilizando SQL puro
    @NativeQuery(value = """
                SELECT e
                FROM exercicios e
                WHERE UPPER(e.grupo_muscular) = UPPER(:grupoMuscular)
            """
    )
    List<ExerciciosEntity> findAllByGrupoMuscularNative(@Param("grupoMuscular") String grupoMuscular);
}
