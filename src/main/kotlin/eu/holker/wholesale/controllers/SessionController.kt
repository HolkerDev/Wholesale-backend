package eu.holker.wholesale.controllers

import eu.holker.wholesale.persistance.dto.request.LoginForm
import eu.holker.wholesale.security.JwtUtils
import eu.holker.wholesale.services.SessionService
import eu.holker.wholesale.services.UserService
import eu.holker.wholesale.utils.errors.LoginFailedException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/session")
class SessionController @Autowired constructor(
    private val jwtUtils: JwtUtils,
    private val userService: UserService,
    private val userDetailsService: UserDetailsService,
    private val sessionService: SessionService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<*> {
        val cookies = request.cookies
        val refreshCookie = cookies.find { it.name == "refresh_token" }
        return if (refreshCookie != null) {
            logger.info { "Refresh token is present. Extracting username" }
            val username = jwtUtils.extractUsernameFromToken(refreshCookie.value)
            val accessCookie =
                Cookie("access_token", jwtUtils.generateToken(userDetailsService.loadUserByUsername(username)))
            response.addCookie(accessCookie)
            ResponseEntity<Nothing>(HttpStatus.OK)
        } else {
            logger.info { "Refresh token is null." }
            ResponseEntity<Nothing>(HttpStatus.UNAUTHORIZED)
        }
    }

    @GetMapping("/login")
    fun login(
        @RequestBody loginForm: LoginForm,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<*> {
        try {
            val user = userService.findByEmail(loginForm.email)
            if (sessionService.isPasswordMatches(loginForm.password, user)) {
                val accessCookie =
                    Cookie("access_token", jwtUtils.generateToken(User(user.email, user.password, user.roles)))
                val refreshCookie =
                    Cookie("access_token", jwtUtils.generateToken(User(user.email, user.password, user.roles)))
                response.addCookie(accessCookie)
                response.addCookie(refreshCookie)
                return ResponseEntity<Nothing>(HttpStatus.OK)
            } else {
                throw Exception("Credentials are wrong")
            }
        } catch (e: Exception) {
            throw LoginFailedException("User doesn't exists")
        }
    }
}