package br.com.issler.spring_boot_essentials.controller;

import br.com.issler.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.issler.spring_boot_essentials.dto.AlunoDTO;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import br.com.issler.spring_boot_essentials.exception.NotFoundException;
import br.com.issler.spring_boot_essentials.service.AlunosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
@Validated
public class AlunosController {
    private final AlunosService alunosService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AlunoDTO alunoDTO) throws BadRequestException {
        alunosService.save(alunoDTO);
    }

    @GetMapping("/{alunoId}/avaliacao")
    public AvaliacoesFisicasEntity getAvaliacao(@PathVariable Integer alunoId) throws NotFoundException {
        return alunosService.getAlunoAvaliacao(alunoId);
    }

    @DeleteMapping("/{alunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer alunoId) throws NotFoundException {
        alunosService.delete(alunoId);
    }

}
