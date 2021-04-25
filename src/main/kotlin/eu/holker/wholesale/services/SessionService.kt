package eu.holker.wholesale.services

import eu.holker.wholesale.persistance.domain.UserEntity
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SessionService @Autowired constructor(private val passwordEncoder: PasswordEncoder) {
    private val logger = KotlinLogging.logger { }

    fun isPasswordMatches(password: String, user: UserEntity): Boolean {
        return passwordEncoder.matches(password, user.password)
    }
}