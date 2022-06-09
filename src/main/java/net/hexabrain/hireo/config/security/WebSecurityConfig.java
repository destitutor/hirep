package net.hexabrain.hireo.config.security;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.config.security.jwt.JwtAccessDeniedHandler;
import net.hexabrain.hireo.config.security.jwt.JwtAuthTokenFilter;
import net.hexabrain.hireo.config.security.jwt.JwtAuthenticationEntryPoint;
import net.hexabrain.hireo.config.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();

        http
                .authorizeHttpRequests(authz -> authz
                        .antMatchers("/api/authenticate").permitAll()
                        .antMatchers("/api/**").authenticated()
                )
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationErrorHandler)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .and()
                .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .addFilterBefore(new JwtAuthTokenFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain loginFormFilterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .antMatchers("/accounts/login", "/accounts/new", "/",
                                "/js/**", "/sass/**", "/images/**", "/fonts/**", "/css/**").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/accounts/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutUrl("/accounts/logout")
                .logoutSuccessUrl("/")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        return http.build();
    }

    @Bean
    public AffirmativeBased defaultAccessDecisionManager(RoleHierarchy roleHierarchy) {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        webExpressionVoter.setExpressionHandler(expressionHandler);

        decisionVoters.add(webExpressionVoter);
        decisionVoters.add(roleHierarchyVoter(roleHierarchy));
        return new AffirmativeBased(decisionVoters);
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter(RoleHierarchy roleHierarchy) {
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_FREELANCER and ROLE_ADMIN > ROLE_EMPLOYER");
        return roleHierarchy;
    }
}
