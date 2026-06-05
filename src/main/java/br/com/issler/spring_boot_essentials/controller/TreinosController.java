package br.com.issler.spring_boot_essentials.controller;

import br.com.issler.spring_boot_essentials.dto.TreinoDTO;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import br.com.issler.spring_boot_essentials.exception.NotFoundException;
import br.com.issler.spring_boot_essentials.service.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/treinos")
@RequiredArgsConstructor
@Validated
public class TreinosController {
    private final TreinoService treinoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody TreinoDTO treinoDTO) throws NotFoundException, BadRequestException {
        treinoService.save(treinoDTO);
    }
}
