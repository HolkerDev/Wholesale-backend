package eu.holker.wholesale.services

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl @Autowired constructor(private val userService: UserService) : UserDetailsService {
    private val logger = KotlinLogging.logger { }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.findByUsername(username ?: "")
        //TODO: Add roles here
        return User(user.username, user.password, listOf())
    }
}