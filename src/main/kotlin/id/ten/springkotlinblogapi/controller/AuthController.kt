package id.ten.springkotlinblogapi.controller

import id.ten.springkotlinblogapi.dto.requests.AuthRequest
import id.ten.springkotlinblogapi.dto.responses.MessageResponse
import id.ten.springkotlinblogapi.error.ValidationErrorProcessor
import id.ten.springkotlinblogapi.exceptions.BadRequestException
import id.ten.springkotlinblogapi.service.AuthUserService
import id.ten.springkotlinblogapi.util.Constant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import javax.validation.Valid

@RestController
@RequestMapping(Constant.AUTH_PATH)
class AuthController(
    private val authService: AuthUserService,
    private val validationErrorProcesstor: ValidationErrorProcessor
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody authRequest: AuthRequest): ResponseEntity<Any> {
        return try {
            authService.login(authRequest)
        }catch (e: Exception){
            val status = when (e) {
                is UsernameNotFoundException -> HttpStatus.BAD_REQUEST
                is BadCredentialsException -> HttpStatus.UNAUTHORIZED
                is BadRequestException -> HttpStatus.FORBIDDEN
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
            ResponseEntity<Any>(MessageResponse(e.message), status)
        }
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody authRequest: AuthRequest, result: BindingResult): ResponseEntity<Any> {
        if(result.hasFieldErrors()) {
            val errorMessage  = validationErrorProcesstor.process(result)
            return ResponseEntity.badRequest().body(MessageResponse(errorMessage))
        }

        return try {
            authService.register(authRequest)
        }catch (e: BadRequestException) {
            return ResponseEntity.badRequest().body(MessageResponse(e.message))
        }
    }

}