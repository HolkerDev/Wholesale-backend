package eu.holker.wholesale.persistance.domain

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    var email: String,
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @Transient
    var roles = listOf<SimpleGrantedAuthority>()
}