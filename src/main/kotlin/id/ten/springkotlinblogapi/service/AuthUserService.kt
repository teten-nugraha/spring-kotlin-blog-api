package id.ten.springkotlinblogapi.service

import id.ten.springkotlinblogapi.dto.requests.AuthRequest
import id.ten.springkotlinblogapi.dto.responses.LoginResponse
import id.ten.springkotlinblogapi.exceptions.BadRequestException
import id.ten.springkotlinblogapi.exceptions.ResourceNotFoundException
import id.ten.springkotlinblogapi.models.Role
import id.ten.springkotlinblogapi.models.User
import id.ten.springkotlinblogapi.repository.UserRepository
import id.ten.springkotlinblogapi.security.JwtUtils
import id.ten.springkotlinblogapi.security.UserDetailImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.jvm.Throws

@Service
class AuthUserService: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user =
            userRepository.findByUsername(username)
                .orElseThrow{ResourceNotFoundException("Not found: $username")}

        return UserDetailImpl(user)
    }

    fun login(authRequest: AuthRequest): ResponseEntity<Any> {
        val userDetail = loadUserByUsername(authRequest.username)

        if(!passwordEncoder.matches(authRequest.password, userDetail.password)){
            throw BadRequestException("Invalid credentials")
        }

        if(!userDetail.isEnabled) {
            throw BadRequestException("The user is not enable")
        }

        val authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password
                )
            )

        SecurityContextHolder.getContext().authentication = authentication

        val jwtToken = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as UserDetailImpl
        val user = userDetails.user
        val roles = userDetails.authorities.stream()
            .map { item -> item.authority }
            .collect(Collectors.toList())

        user.token = jwtToken
        userRepository.save(user)

        return ResponseEntity.ok().body(
            LoginResponse(
                jwtToken,
                userDetails.getId()?: 0,
                userDetails.username,
                user.isAdmin()
            )
        )
    }

    fun register(authRequest: AuthRequest): ResponseEntity<Any> {
        if(userRepository.existsByUsername(authRequest.username)) {
            throw BadRequestException("username is already taken")
        }

        User(
            username = authRequest.username,
            password = passwordEncoder.encode(authRequest.password),
            roles = hashSetOf(Role.ROLE_USER, Role.ROLE_ADMIN)
        ).apply { userRepository.save(this) }
        return login(authRequest)
    }
}