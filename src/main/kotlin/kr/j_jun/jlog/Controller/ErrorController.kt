package kr.j_jun.jlog.Controller

import kr.j_jun.jlog.DTO.Response
import kr.j_jun.jlog.Enum.ResponseStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 예외(오류)에 대한 핸들링을 하기위한 Controller Class
 * */
@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): Response {

        val exceptionType = when(exception) {
            is ClassNotFoundException -> ResponseStatus.CLASS_NOT_FOUND
            else -> ResponseStatus.FAILED
        }

        return Response(exceptionType, exception.message)
    }
}