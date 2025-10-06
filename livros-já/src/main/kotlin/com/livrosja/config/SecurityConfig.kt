package com.livrosja.config

import com.livrosja.enums.Roles
import com.livrosja.repository.CustomerRepository
import com.livrosja.security.AuthenticationFilter
import com.livrosja.security.AuthorizationFilter
import com.livrosja.security.JwtUtil
import com.livrosja.service.UserDetailCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
) {

    private val publicPosts = arrayOf("/customers")

    private val admin_matchers = arrayOf("/admin/**")



    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    } // Bean de filtro na autenticação

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    } // Bean para criptografar senha

    @Bean
    fun authenticationProvider(userDetailsService: UserDetailsService, passwordEncoder: PasswordEncoder): AuthenticationProvider {
        val provider = DaoAuthenticationProvider(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }

    @Bean
    fun filterChain(httpSecurity: HttpSecurity, authenticationManager: AuthenticationManager, jwtUtil: JwtUtil, userDetails: UserDetailCustomService): SecurityFilterChain {

        return httpSecurity

            .cors{ cors -> cors.disable() }
            .csrf{ csrf -> csrf.disable() }

            .authorizeHttpRequests{
                auth -> auth
                .requestMatchers(HttpMethod.POST, *publicPosts).permitAll()
                .requestMatchers(*admin_matchers).hasAuthority(Roles.ADMIN.description)
                .anyRequest().authenticated()
            }

            .addFilter(AuthenticationFilter(authenticationManager, customerRepository, jwtUtil)) // filtro autenticação
            .addFilter(AuthorizationFilter(authenticationManager, jwtUtil, userDetails)) // filtro de autorização (rotas)

            .sessionManagement{ sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }


}

