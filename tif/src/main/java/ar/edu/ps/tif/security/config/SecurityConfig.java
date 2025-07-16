package ar.edu.ps.tif.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ar.edu.ps.tif.security.config.filter.JwtTokenValidator;
import ar.edu.ps.tif.security.config.oauth.OAuth2SuccessHandler;
import ar.edu.ps.tif.service.CustomOAuth2UserService;
import ar.edu.ps.tif.utils.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return httpSecurity
    //             .csrf(csrf -> csrf.disable())
    //             //.formLogin(Customizer.withDefaults())
    //             .httpBasic(Customizer.withDefaults())
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
    //             .oauth2Login(oauth -> oauth
    //                     .userInfoEndpoint(user -> user
    //                             .userService(customOAuth2UserService))
    //                     .defaultSuccessUrl("/html/oauth2-success.html", true) // 👈 redirige aquí luego del login
    //             )
    //             .build();
    // }
    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/oauth/success", "/oauth2/**", "/html/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
            .oauth2Login(oauth -> oauth
                    .userInfoEndpoint(user -> user
                            .userService(customOAuth2UserService))
                    // .defaultSuccessUrl("/auth/oauth/success", true)
                    .successHandler(oAuth2SuccessHandler)
            )
            .build();
}
    

    // Creamos el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Creamos el AuthenticationProvider
    // Agregamos el UserDetailsService como parámetro
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService); // Usamos el UserDetailsService correctamente

        return provider;
    }

    // password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
