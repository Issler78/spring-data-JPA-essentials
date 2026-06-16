package br.com.issler.spring_boot_essentials.dto;

public record TokenResponseDTO(String token, long expires_in) {
}
