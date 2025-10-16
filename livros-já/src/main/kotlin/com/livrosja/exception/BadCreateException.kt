package com.livrosja.exception

class BadCreateException(
    override val message: String,
    val errorCode: String,
): Exception()
