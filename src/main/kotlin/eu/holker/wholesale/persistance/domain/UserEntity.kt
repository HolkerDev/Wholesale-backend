package eu.holker.wholesale.persistance.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    var username: String,
    var email: String,
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    var roles = listOf<SimpleGrantedAuthority>()
}