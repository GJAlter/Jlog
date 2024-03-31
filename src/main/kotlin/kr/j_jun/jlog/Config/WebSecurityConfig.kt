package kr.j_jun.jlog.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors {
            it.disable()
        }
        http.csrf{
            it.disable()
        }
        http.authorizeHttpRequests { authorizeRequests ->
            authorizeRequests.requestMatchers("/register").permitAll().anyRequest().authenticated()
        }.formLogin { login ->
            login.usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
        }

        return http.build()
    }

   @Bean
   fun passwordEncoder(): PasswordEncoder {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
   }
}