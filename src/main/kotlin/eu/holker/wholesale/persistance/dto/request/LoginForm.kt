package eu.holker.wholesale.persistance.dto.request

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming((PropertyNamingStrategy.SNAKE_CASE::class))
data class LoginForm(
    val email: String,
    val password: String
)