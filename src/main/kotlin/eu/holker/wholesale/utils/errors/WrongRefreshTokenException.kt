package eu.holker.wholesale.utils.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class WrongRefreshTokenException(message: String) : Exception(message)