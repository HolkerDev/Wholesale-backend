package eu.holker.wholesale.security

import eu.holker.wholesale.utils.errors.WrongTokenException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtils {
    @Value(value = "\${jwt.secret}")
    lateinit var secretKey: String

    @Value(value = "\${jwt.accessToken.expirationTime}")
    lateinit var tokenExpirationTime: String

    @Value(value = "\${jwt.refreshToken.expirationTime}")
    lateinit var refreshExpirationTime: String

    fun generateToken(userDetails: UserDetails): String {
        val roles = userDetails.authorities.map { it.authority }
        return doGenerateToken(roles, userDetails.username)
    }

    private fun doGenerateToken(roles: List<String>, subject: String): String {
        return Jwts.builder().setClaims(mapOf("roles" to roles)).setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + tokenExpirationTime.toLong()))
            .signWith(SignatureAlgorithm.ES256, secretKey).compact()
    }

    private fun doGenerateRefreshToken(subject: String): String {
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + refreshExpirationTime.toLong()))
            .signWith(SignatureAlgorithm.ES256, secretKey).compact()
    }

    fun extractUsernameFromToken(token: String): String {
        try {
            val jwtToken = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return jwtToken.body.subject
        } catch (e: Exception) {
            throw WrongTokenException("Provided token is wrong.")
        }
    }

    fun validateAccessToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            throw WrongTokenException("Provided token is wrong.")
        }
    }
}