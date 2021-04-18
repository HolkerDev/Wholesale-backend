package eu.holker.wholesale.repositories

import eu.holker.wholesale.persistance.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface IUserRepository : JpaRepository<UserEntity, Int> {
    fun findByUsername(username: String): Optional<UserEntity>
}