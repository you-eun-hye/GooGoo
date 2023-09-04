package eunhye.GooGoo.config.security;

import eunhye.GooGoo.config.jwt.JwtAuthenticationFilter;
import eunhye.GooGoo.config.jwt.JwtTokenProvider;
import eunhye.GooGoo.config.oauth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final AuthenticationFailureHandler customFailureHandler;
    private final Oauth2UserService oauth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(requests -> requests
                        .requestMatchers("/api/v1/admin/**").access("hasAuthority('ADMIN')")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeRequests(requests -> requests
//                        .requestMatchers("/user/**").authenticated()
//                        .requestMatchers("/admin/**").access("hasAuthority('ADMIN')")
//                        .anyRequest().permitAll()
//                )
//                .formLogin(form->form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/home")
//                        .usernameParameter("userEmail").passwordParameter("userPassword")
//                        .failureHandler(customFailureHandler)
//                )
//                .logout(logout->logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessUrl("/login")
//                )
//                .oauth2Login(oauth2->oauth2
//                        .loginPage("/login").defaultSuccessUrl("/home")
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))
//                );
        return http.build();
    }
}
