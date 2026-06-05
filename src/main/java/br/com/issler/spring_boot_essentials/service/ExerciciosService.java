package br.com.issler.spring_boot_essentials.service;

import br.com.issler.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.issler.spring_boot_essentials.database.repository.IExerciciosRepository;
import br.com.issler.spring_boot_essentials.dto.ExercicioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciciosService {
    // so é possivel injetar dependencias em classes sendo um Bin gerenciável
    private final IExerciciosRepository exerciciosRepository;

    public List<ExerciciosEntity> findAll(){
        return exerciciosRepository.findAll();
    }

    public void save(ExercicioDTO exercicioDTO){
        // criando um novo exercicio
        ExerciciosEntity novoExercicio = ExerciciosEntity.builder()
                .nome(exercicioDTO.getNome())
                .grupoMuscular(exercicioDTO.getGrupoMuscular())
                .build();

        // salvando o exercicio no banco de dados
        exerciciosRepository.save(novoExercicio);
    }

    public List<ExerciciosEntity> getExerciciosByGrupoMuscular(String grupoMuscular){
        return exerciciosRepository.findAllByGrupoMuscular(grupoMuscular);
    }
}
