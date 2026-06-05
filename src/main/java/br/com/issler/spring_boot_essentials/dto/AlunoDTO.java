package br.com.issler.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;
}
