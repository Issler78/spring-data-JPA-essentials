package br.com.issler.spring_boot_essentials.service;

import br.com.issler.spring_boot_essentials.database.model.AlunosEntity;
import br.com.issler.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.issler.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.issler.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.issler.spring_boot_essentials.dto.AvaliacaoFisicaDTO;
import br.com.issler.spring_boot_essentials.dto.AvaliacoesFisicasProjection;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import br.com.issler.spring_boot_essentials.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoFisicaService {
    private final IAlunosRepository alunosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;

    public void save(AvaliacaoFisicaDTO avaliacaoFisicaDTO) throws NotFoundException, BadRequestException {
        // tentar encontrar o aluno
        AlunosEntity aluno = alunosRepository.findById(avaliacaoFisicaDTO.getAlunoId())
                .orElseThrow(() -> new NotFoundException(
                        "Aluno não encontrado com id: " + avaliacaoFisicaDTO.getAlunoId()
                ));

        // tentar encontrar avaliação fisica existente
        AvaliacoesFisicasEntity avaliacaoFisica = aluno.getAvaliacaoFisica();
        if (avaliacaoFisica != null) {
            throw new BadRequestException("Aluno já possui uma avaliação física cadastrada");
        }

        // criar nova avaliacao
        AvaliacoesFisicasEntity novaAvaliacao = AvaliacoesFisicasEntity
                .builder()
                .peso(avaliacaoFisicaDTO.getPeso())
                .altura(avaliacaoFisicaDTO.getAltura())
                .porcentagemGorduraCorporal(avaliacaoFisicaDTO.getPorcentagemGorduraCorporal())
                .build();



        // salvar a avaliação física e setar a avaliação física no aluno

        // forma 1
//        AvaliacoesFisicasEntity saved = avaliacoesFisicasRepository.save(novaAvaliacao);
//        aluno.setAvaliacaoFisica(saved);
//        alunosRepository.save(aluno);

        // forma 2 - com o cascade
        // setar apenas a avaliação física no aluno, o cascade vai cuidar de salvar a avaliação física
        aluno.setAvaliacaoFisica(novaAvaliacao);
        alunosRepository.save(aluno);
    }

    public List<AvaliacoesFisicasProjection> getAllAvaliacoes() {
        return avaliacoesFisicasRepository.getAllAvaliacoes();
    }

    public Page<AvaliacoesFisicasProjection> getAllAvaliacoesPageable(Integer page, Integer size){
        return avaliacoesFisicasRepository.getAllAvaliacoesPaginate(PageRequest.of(page, size));
    }
}
