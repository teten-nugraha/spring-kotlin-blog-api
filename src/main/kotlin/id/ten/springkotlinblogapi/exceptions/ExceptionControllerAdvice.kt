package id.ten.springkotlinblogapi.exceptions

import id.ten.springkotlinblogapi.error.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorMessage> {
        val errorMsg = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMsg, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handlerResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorMessage> {
        val errorMsg = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMsg, HttpStatus.NOT_FOUND)
    }

}