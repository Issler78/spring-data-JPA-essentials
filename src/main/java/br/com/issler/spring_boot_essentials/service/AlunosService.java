package br.com.issler.spring_boot_essentials.service;

import br.com.issler.spring_boot_essentials.database.model.AlunosEntity;
import br.com.issler.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.issler.spring_boot_essentials.database.model.TreinosEntity;
import br.com.issler.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.issler.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.issler.spring_boot_essentials.database.repository.ITreinosRepository;
import br.com.issler.spring_boot_essentials.dto.AlunoDTO;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import br.com.issler.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunosService {
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;
    private final ITreinosRepository treinosRepository;
    private final IAlunosRepository alunosRepository;

    public void save(AlunoDTO alunoDTO) throws BadRequestException {
        AlunosEntity aluno = alunosRepository.findByEmail(alunoDTO.getEmail())
                .orElse(null);

        // verifica se existe um aluno com este email
        if (aluno != null) {
            throw new BadRequestException("Aluno já cadastrado com esse email");
        }

        alunosRepository.save(AlunosEntity.builder()
                .nome(alunoDTO.getNome())
                .email(alunoDTO.getEmail())
                .build()
        );
    }

    public AvaliacoesFisicasEntity getAlunoAvaliacao(Integer alunoId) throws NotFoundException {
        AlunosEntity aluno = alunosRepository.findByIdFetch(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + alunoId.toString()));

        AvaliacoesFisicasEntity avaliacao = aluno.getAvaliacaoFisica();
        if (avaliacao == null) {
            throw new NotFoundException("Avaliação física não encontrada para o aluno com id: " + alunoId.toString());
        }

        return avaliacao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer alunoId) throws NotFoundException {
        // transaction begin

        AlunosEntity aluno = alunosRepository.findByIdFetch(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + alunoId.toString()));

        // deletar treinos
        List<Integer> treinosIds = aluno.getTreinos().stream()
                .map(TreinosEntity::getId)
                .toList();

        treinosRepository.deleteAllById(treinosIds);

        // deletar aluno
        alunosRepository.deleteById(alunoId);

        // deletar avaliacao
        // ERRO INESPERADO
        // transaction rollback
        avaliacoesFisicasRepository.deleteById(aluno.getAvaliacaoFisica().getId());

        // transaction commit
    }
}
