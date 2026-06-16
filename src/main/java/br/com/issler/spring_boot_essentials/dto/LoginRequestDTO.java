package br.com.issler.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String senha;
}
