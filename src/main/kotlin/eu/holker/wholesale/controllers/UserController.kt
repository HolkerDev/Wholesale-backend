package eu.holker.wholesale.controllers

import eu.holker.wholesale.persistance.dto.request.RegisterForm
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/register")
    fun register(@RequestBody registerForm: RegisterForm): ResponseEntity<*> {
        TODO("Implement register logic")
    }
}