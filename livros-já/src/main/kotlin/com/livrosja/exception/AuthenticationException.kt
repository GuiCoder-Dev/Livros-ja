package com.livrosja.exception

class AuthenticationException(
    override val message : String,
    val errorCode: String
): Exception()


