package br.com.issler.spring_boot_essentials.controller;

import br.com.issler.spring_boot_essentials.dto.LoginRequestDTO;
import br.com.issler.spring_boot_essentials.dto.RegisterRequestDTO;
import br.com.issler.spring_boot_essentials.dto.TokenResponseDTO;
import br.com.issler.spring_boot_essentials.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) throws Exception {
        authService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception {
        return authService.login(loginRequestDTO);
    }
}
