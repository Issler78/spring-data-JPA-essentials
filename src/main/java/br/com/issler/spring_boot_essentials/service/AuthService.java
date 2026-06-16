package br.com.issler.spring_boot_essentials.service;

import br.com.issler.spring_boot_essentials.config.TokenProvider;
import br.com.issler.spring_boot_essentials.database.model.AlunosEntity;
import br.com.issler.spring_boot_essentials.database.model.RolesEntity;
import br.com.issler.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.issler.spring_boot_essentials.database.repository.IRolesRepository;
import br.com.issler.spring_boot_essentials.dto.LoginRequestDTO;
import br.com.issler.spring_boot_essentials.dto.RegisterRequestDTO;
import br.com.issler.spring_boot_essentials.dto.TokenResponseDTO;
import br.com.issler.spring_boot_essentials.enums.RoleTypeEnum;
import br.com.issler.spring_boot_essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IAlunosRepository alunosRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${spring.jwt.expiration}")
    private long expirationTime;

    public void register(RegisterRequestDTO registerRequestDTO) throws BadRequestException {
        AlunosEntity aluno = alunosRepository.findByEmail(registerRequestDTO.getEmail())
                .orElse(null);

        // verifica se existe um aluno com este email
        if (aluno != null) {
            throw new BadRequestException("Aluno já cadastrado com esse email");
        }


        // verifica se existe o papel de aluno, caso não exista, cria um novo
        RolesEntity role = rolesRepository.findByName(RoleTypeEnum.ROLE_ALUNO.name())
                .orElseGet(() -> rolesRepository.save(RolesEntity.builder()
                        .name(RoleTypeEnum.ROLE_ALUNO.name())
                        .build()
                ));


        // salva o usuario
        alunosRepository.save(AlunosEntity.builder()
                .nome(registerRequestDTO.getNome())
                .email(registerRequestDTO.getEmail())
                .senha(passwordEncoder.encode(registerRequestDTO.getSenha()))
                .roles(Set.of(role))
                .build()
        );
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getSenha()
                    )
            );

            String token = tokenProvider.generateToken(auth);

            return new TokenResponseDTO(token, expirationTime);

            // authenticate -> authentication provider -> userDetailsService -> passwordEncoder.matches -> autenticado
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Email ou senha inválidos");
        }
        catch (Exception e) {
            throw new Exception("Erro ao autenticar usuário");
        }
    }
}
