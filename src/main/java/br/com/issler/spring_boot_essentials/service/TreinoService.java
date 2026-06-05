package br.com.issler.spring_boot_essentials.service;

import br.com.issler.spring_boot_essentials.database.model.AlunosEntity;
import br.com.issler.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.issler.spring_boot_essentials.database.model.TreinosEntity;
import br.com.issler.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.issler.spring_boot_essentials.database.repository.IExerciciosRepository;
import br.com.issler.spring_boot_essentials.database.repository.ITreinosRepository;
import br.com.issler.spring_boot_essentials.dto.TreinoDTO;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import br.com.issler.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final ITreinosRepository treinosRepository;
    private final IExerciciosRepository exerciciosRepository;
    private final IAlunosRepository alunosRepository;

    public void save(TreinoDTO treinoDTO) throws NotFoundException, BadRequestException {
        Set<ExerciciosEntity> exercicios = new HashSet<>();

        AlunosEntity aluno = alunosRepository.findById(treinoDTO.getAlunoId())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        // verifica se o aluno já tem um treino com o mesmo nome
        TreinosEntity treino = treinosRepository.findByNomeAndAlunoId(treinoDTO.getNome(), treinoDTO.getAlunoId())
                .orElse(null);

        if (treino != null) {
            throw new BadRequestException("Aluno já possui um treino com esse nome");
        }


        // verifica se os exercícios existem e adiciona na lista
        for (Integer exercicioId : treinoDTO.getExerciciosIds()) {
            ExerciciosEntity exercicio = exerciciosRepository.findById(exercicioId)
                    .orElseThrow(() -> new NotFoundException(String.format("Exercício %s não encontrado", exercicioId)));

            exercicios.add(exercicio);
        }


        // salvando o treino
        treino = TreinosEntity.builder()
                .nome(treinoDTO.getNome())
                .aluno(aluno)
                .exercicios(exercicios)
                .build();

        treinosRepository.save(treino);
    }
}
