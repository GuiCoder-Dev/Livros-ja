package com.livrosja.exception

class BadBuyException(
    override val message: String,
    val errorCode: String
): Exception()


