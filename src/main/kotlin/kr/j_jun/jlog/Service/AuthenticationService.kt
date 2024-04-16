package kr.j_jun.jlog.Service

import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.DTO.User
import kr.j_jun.jlog.Entity.Users
import kr.j_jun.jlog.Enum.ResponseStatus
import kr.j_jun.jlog.Exceptions
import kr.j_jun.jlog.Repository.UsersRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AuthenticationService(
    private val passwordEncoder: PasswordEncoder,
    private val authManagerBuilder: AuthenticationManagerBuilder,
    private val usersRepo: UsersRepository
): UserDetailsService {

    fun register(user: User.Register): Response {
        usersRepo.save(Users(
            userId = user.id,
            pw = passwordEncoder.encode(user.password),
            nickname = user.nickname
        ))

        return Response(ResponseStatus.OK)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = usersRepo.findById(username).getOrNull()?: throw Exceptions.DataNotFoundException("사용자를 찾을수 없습니다.")

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.userId)
            .password(user.password)
            .build()
    }
}