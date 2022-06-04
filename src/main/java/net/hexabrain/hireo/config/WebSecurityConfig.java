package net.hexabrain.hireo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .antMatchers("/account/login", "/account/new", "/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin()
                .loginPage("/account/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/", true)
                .and()
            .logout()
                .logoutUrl("/account/logout")
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
