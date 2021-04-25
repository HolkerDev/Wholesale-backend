package eu.holker.wholesale.utils.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class LoginFailedException(msg: String) : Exception(msg)