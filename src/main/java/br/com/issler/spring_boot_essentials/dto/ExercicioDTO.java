package br.com.issler.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ExercicioDTO {

    @NotBlank(message = "O nome é obrigatório") // nao pode ser nula e nem vazio
    private String nome;

    @NotBlank(message = "O grupo muscular é obrigatório")
    private String grupoMuscular;

}
