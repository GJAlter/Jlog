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
        val checkDuplicate = (checkDuplicate(user.id, user.nickname))
        if((checkDuplicate.body as Response.ResponseData).statusCode != 200) {
            return checkDuplicate
        }
        usersRepo.save(Users(
            userId = user.id,
            pw = passwordEncoder.encode(user.password),
            nickname = user.nickname,
            notion = user.notion
        ))

        return Response(ResponseStatus.OK)
    }

    fun checkDuplicate(userId: String?, nickname: String?): Response {
        if(userId != null && usersRepo.existsById(userId)) throw Exceptions.DuplicateException("아이디가 중복되었습니다.")
        if(nickname != null && usersRepo.existsByNickname(nickname)) throw Exceptions.DuplicateException("닉네임이 중복되었습니다.")

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

        return Response(ResponseStatus.OK, u.nickname)
    }

    fun getNotionId(userId: String): Response {
        val u = usersRepo.findById(userId).getOrNull()?: throw Exceptions.DataNotFoundException("사용자를 찾을 수 없습니다.")

        return Response(ResponseStatus.OK, u.notion)
    }

}