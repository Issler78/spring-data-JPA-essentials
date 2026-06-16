package br.com.issler.spring_boot_essentials.config;

import br.com.issler.spring_boot_essentials.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // valid token
            String token = authorizationHeader.substring(7);

            if (tokenProvider.isTokenValid(token)) {
                // get username from token
                String username = tokenProvider.getUsername(token);

                // load user by username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // create authentication token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // set authentication on security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            }
        }

        filterChain.doFilter(request, response);
    }
}
