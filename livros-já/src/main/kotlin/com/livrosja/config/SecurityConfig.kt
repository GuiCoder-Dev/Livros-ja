package com.livrosja.config

import com.livrosja.enums.Roles
import com.livrosja.repository.CustomerRepository
import com.livrosja.security.AuthenticationFilter
import com.livrosja.security.AuthorizationFilter
import com.livrosja.security.CustomAuthenticationEntryPoint
import com.livrosja.security.JwtUtil
import com.livrosja.service.UserDetailCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val customAuthentication: CustomAuthenticationEntryPoint
) {

    private val publicPosts = arrayOf("/customers")

    private val adminMatchers = arrayOf("/admin/**", "/customers", "/customers/actives", "/books/all")

    private val swaggerMatchers = arrayOf("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**", "/v3/api-docs", "/swagger-ui/**")

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

//    @Bean
//    fun corsConfig(): CorsFilter{
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//        config.allowCredentials = true
//        config.addAllowedOrigin("*")
//        config.addAllowedHeader("*")
//        config.addAllowedMethod("*")
//        source.registerCorsConfiguration("/**", config)
//        return CorsFilter(source)
//    }

    @Bean
    fun filterChain(httpSecurity: HttpSecurity, authenticationManager: AuthenticationManager, jwtUtil: JwtUtil, userDetails: UserDetailCustomService): SecurityFilterChain {

        return httpSecurity

            .cors{ cors -> cors.disable() }
            .csrf{ csrf -> csrf.disable() }

            .authorizeHttpRequests{
                auth -> auth
                .requestMatchers(HttpMethod.POST, *publicPosts).permitAll()
                .requestMatchers(*adminMatchers).hasAuthority(Roles.ADMIN.description)
                .requestMatchers(*swaggerMatchers).permitAll()
                .anyRequest().authenticated()
            }

            // qualquer erro de autorização e autenticação, irá cair aqui
            .exceptionHandling { exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(customAuthentication)}
            .addFilter(AuthenticationFilter(authenticationManager, customerRepository, jwtUtil)) // filtro autenticação
            .addFilter(AuthorizationFilter(authenticationManager, jwtUtil, userDetails)) // filtro de autorização (rotas)

            .sessionManagement{ sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }




}

