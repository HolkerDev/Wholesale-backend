package eu.holker.wholesale.security.filters

import eu.holker.wholesale.security.JwtUtils
import eu.holker.wholesale.utils.errors.WrongTokenException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CookieFilter : OncePerRequestFilter() {
    private val logger = KotlinLogging.logger { }

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessCookie = request.cookies.find { it.name == "access_cookie" }
            if (accessCookie != null && jwtUtils.validateAccessToken(accessCookie.value)) {
                val userDetails = User(jwtUtils.extractUsernameFromToken(accessCookie.value), "", listOf())
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            }
        } catch (e: Exception) {
            throw WrongTokenException("Token is incorrect.")
        }
        filterChain.doFilter(request, response)
    }
}