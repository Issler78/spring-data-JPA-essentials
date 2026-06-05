package br.com.issler.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreinoDTO {
    @NotNull
    private Integer alunoId;

    @NotBlank
    private String nome;

    @NotEmpty
    private List<Integer> exerciciosIds;
}
