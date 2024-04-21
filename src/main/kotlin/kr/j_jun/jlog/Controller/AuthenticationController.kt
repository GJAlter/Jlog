package kr.j_jun.jlog.Controller

import jakarta.servlet.http.HttpServletRequest
import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.DTO.User
import kr.j_jun.jlog.Service.AuthenticationService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class AuthenticationController(private val service: AuthenticationService) {

    @PostMapping("/register")
    fun register(@RequestBody register: User.Register): Response {
        return service.register(register)
    }

    @GetMapping("/duplicate")
    fun checkDuplicateId(
        @RequestParam("id") userId: String?,
        @RequestParam("nickname") nickname: String?
    ): Response {
        return service.checkDuplicate(userId, nickname)
    }

    @PostMapping("/login")
    fun login(
        req: HttpServletRequest,
        user: User.Login
    ): Response {
        return service.login(req, user)
    }

    @PostMapping("/logout")
    fun logout(
        req: HttpServletRequest
    ): Response {
        return service.logout(req)
    }

    @GetMapping("/check")
    fun checkLogin(
        @SessionAttribute(name = "userId", required = true) userId: String
    ): Response {
        return service.checkLogin(userId)
    }

    @GetMapping("/notion")
    fun getNotionId(
        @SessionAttribute(name = "userId", required = true) userId: String
    ): Response {
        return service.getNotionId(userId)
    }
}