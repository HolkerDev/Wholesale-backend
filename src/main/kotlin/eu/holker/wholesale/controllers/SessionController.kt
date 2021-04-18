package eu.holker.wholesale.controllers

import eu.holker.wholesale.security.JwtUtils
import eu.holker.wholesale.services.UserService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.GetMapping
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
    private val userDetailsService: UserDetailsService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<*> {
        val cookies = request.cookies
        val refreshCookie = cookies.find { it.name == "refresh_token" }
        return if (refreshCookie != null) {
            val username = jwtUtils.extractUsername(refreshCookie.value)
            val accessCookie =
                Cookie("access_token", jwtUtils.generateToken(userDetailsService.loadUserByUsername(username)))
            response.addCookie(accessCookie)
            ResponseEntity<Nothing>(HttpStatus.OK)
        } else {
            ResponseEntity<Nothing>(HttpStatus.UNAUTHORIZED)
        }
    }
}