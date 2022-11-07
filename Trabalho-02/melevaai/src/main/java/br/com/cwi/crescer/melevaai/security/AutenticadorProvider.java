package br.com.cwi.crescer.melevaai.security;

import br.com.cwi.crescer.melevaai.security.bh.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class AutenticadorProvider extends AbstractUserDetailsAuthenticationProvider {

    @Value("${auth-url}")
    private String authUrl;

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails,
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(
        String username,
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        String token = username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("Authorization", token);

        HttpEntity request = new HttpEntity(headers);

        try {

            ResponseEntity<UserResponse> response = new RestTemplate().exchange(
                authUrl,
                HttpMethod.GET,
                request,
                UserResponse.class
            );

            UserResponse userAuth = response.getBody();

            return new UsuarioAutenticado(
                userAuth.getFirstName(),
                userAuth.getLastName(),
                userAuth.getEmail(),
                userAuth.getRoles());

        } catch (Exception exception) {
            // status 401 quando ocorre erro na api de autenticação (erro de crendenciais)
            throw new UsernameNotFoundException("Usuário não econtrado");
        }
    }
}
