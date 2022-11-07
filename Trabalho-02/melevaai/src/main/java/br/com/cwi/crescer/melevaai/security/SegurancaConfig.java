package br.com.cwi.crescer.melevaai.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticadorProvider provider;

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
            .ignoring()
            .antMatchers("/publico/**");
    }

    private static final RequestMatcher URLS_PROTEGIDAS = new OrRequestMatcher(
        new AntPathRequestMatcher("/privado/**")
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .exceptionHandling()
            .and()
            .authenticationProvider(provider)
            .addFilterBefore(tokenFiltro(), AnonymousAuthenticationFilter.class)
            .authorizeRequests()
            .requestMatchers(URLS_PROTEGIDAS)
            .authenticated()
            .and()
            .csrf().disable()
            ;
    }

    @Bean
    AutenticacaoFilter tokenFiltro() throws Exception {
        final AutenticacaoFilter filter = new AutenticacaoFilter(URLS_PROTEGIDAS);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
