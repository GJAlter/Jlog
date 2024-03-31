package kr.j_jun.jlog.Controller

import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.DTO.User
import kr.j_jun.jlog.Service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(private val service: AuthenticationService) {

    @PostMapping("/register")
    fun register(@RequestBody register: User.Register): Response {
        return service.register(register)
    }
}