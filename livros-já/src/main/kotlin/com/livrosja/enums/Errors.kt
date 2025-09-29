package com.livrosja.enums

enum class Errors(
    val code: String,
    val message: String
) {
    // 1001 a 1100 (Book Errors)
    LJ1001("LJ-1001", "Book [%s] not exists"),
    LJ1002("LJ-1002", "Canoot update book with status [%s]"),

    // 1101 a 1200 (Costumer Errors)
    LJ1101("LJ-0003", "Customer [%s] not exists"),

    // 1201 a 1300 (Requests Errors)
    LJ1201("LJ-0004", "Invalid request"),

    // 1301 a 1400 (Purchase Errors)
    LJ1301("LJ-0005", "Cannot buy a sold book"),

    // 1401 a 1500 (Login Errors)
    LJ1401("LJ-0006", "Failed authentication"),
}