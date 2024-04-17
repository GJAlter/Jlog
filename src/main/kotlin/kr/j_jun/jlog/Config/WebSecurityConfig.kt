package kr.j_jun.jlog.Config

import org.apache.tomcat.util.http.Rfc6265CookieProcessor
import org.apache.tomcat.util.http.SameSiteCookies
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors {
            it.disable()
        }
        http.csrf{
            it.disable()
        }
        http.authorizeHttpRequests { authorizeRequests ->
            authorizeRequests.requestMatchers("/register").permitAll().requestMatchers("/**").permitAll().anyRequest().authenticated()
        }.formLogin { login ->
            login.disable()
        }

        http.sessionManagement {
            it.maximumSessions(1).maxSessionsPreventsLogin(false)
        }

        return http.build()
    }

   @Bean
   fun passwordEncoder(): PasswordEncoder {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
   }

//    @Bean
//    fun sameSiteCookiesConfig(): TomcatContextCustomizer {
//        return TomcatContextCustomizer {
//            val cookieProcessor = Rfc6265CookieProcessor()
//            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.value)
//            it.cookieProcessor = cookieProcessor
//        }
//    }
}