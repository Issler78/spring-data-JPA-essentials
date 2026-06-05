package br.com.issler.spring_boot_essentials.controller;

import br.com.issler.spring_boot_essentials.dto.AvaliacaoFisicaDTO;
import br.com.issler.spring_boot_essentials.dto.AvaliacoesFisicasProjection;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import br.com.issler.spring_boot_essentials.exception.NotFoundException;
import br.com.issler.spring_boot_essentials.service.AvaliacaoFisicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/avaliacoes")
@RequiredArgsConstructor
@Validated
public class AvaliacoesFisicasController {
    private final AvaliacaoFisicaService avaliacaoFisicaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvaliacoesFisicasProjection> getAllAvaliacoes() {
        return avaliacaoFisicaService.getAllAvaliacoes();
    }

    @GetMapping("/page/{pg}/size/{sz}")
    @ResponseStatus(HttpStatus.OK)
    public Page<AvaliacoesFisicasProjection> getAllAvaliacoesPageable(@PathVariable Integer pg, @PathVariable Integer sz) {
        return avaliacaoFisicaService.getAllAvaliacoesPageable(pg, sz);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AvaliacaoFisicaDTO avaliacaoFisicaDTO) throws NotFoundException, BadRequestException {
        avaliacaoFisicaService.save(avaliacaoFisicaDTO);
    }
}
