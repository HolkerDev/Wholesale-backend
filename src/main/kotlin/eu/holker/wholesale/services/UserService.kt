package eu.holker.wholesale.services

import eu.holker.wholesale.persistance.domain.UserEntity
import eu.holker.wholesale.repositories.IUserRepository
import javassist.NotFoundException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: IUserRepository) {
    private val logger = KotlinLogging.logger { }

    fun findByUsername(username: String): UserEntity {
        return userRepository.findByUsername(username)
            .orElseThrow { NotFoundException("User with username $username is not found") }
    }
}