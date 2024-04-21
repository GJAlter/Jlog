package kr.j_jun.jlog.Service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
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
) {

    fun register(user: User.Register): Response {
        usersRepo.save(Users(
            userId = user.id,
            pw = passwordEncoder.encode(user.password),
            nickname = user.nickname
        ))

        return Response(ResponseStatus.OK)
    }

    fun login(req: HttpServletRequest, user: User.Login): Response {
        val u = usersRepo.findById(user.userId).getOrNull()?: throw Exceptions.DataNotFoundException("사용자를 찾을 수 없습니다.")
        if(!passwordEncoder.matches(user.password, u.pw)) {
            throw Exceptions.DataNotFoundException("사용자를 찾을 수 없습니다.")
        }

        val session = req.getSession(true)
        session.setAttribute("userId", u.userId)

        return Response(ResponseStatus.OK)
    }

    fun logout(req: HttpServletRequest): Response {
        req.getSession(false).invalidate()

        return Response(ResponseStatus.OK)
    }

    fun checkLogin(userId: String): Response {
        val u = usersRepo.findById(userId).getOrNull()?: throw Exceptions.DataNotFoundException("사용자를 찾을 수 없습니다.")

        return Response(ResponseStatus.OK, u.userId)
    }

}