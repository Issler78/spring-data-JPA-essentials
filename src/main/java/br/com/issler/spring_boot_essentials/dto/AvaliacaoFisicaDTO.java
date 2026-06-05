package br.com.issler.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AvaliacaoFisicaDTO {

    @NotNull
    private Integer alunoId;

    @NotNull
    private BigDecimal peso;

    @NotNull
    private BigDecimal altura;

    @NotNull
    private BigDecimal porcentagemGorduraCorporal;

}
