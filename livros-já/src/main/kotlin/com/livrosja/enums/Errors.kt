package com.livrosja.enums

enum class Errors(
    val code: String,
    val message: String
) {
    // 1001 a 1100 (Book Errors)
    LJ1001("LJ-1001", "Book [%s] not exists"),
    LJ1002("LJ-1002", "Canoot update book with status [%s]"),
    LJ1003("LJ-1003", "It is not possible to create the book when it is inactive"),

    // 1101 a 1200 (Costumer Errors)
    LJ1101("LJ-1101", "Customer [%s] not exists"),

    // 1201 a 1300 (Requests Errors)
    LJ1201("LJ-1201", "Invalid request"),

    // 1301 a 1400 (Purchase Errors)
    LJ1301("LJ-1301", "Cannot buy a sold book"),

    // 1401 a 1500 (Login Errors)
    LJ1401("LJ-1401", "Failed authentication"),
    LJ1402("LJ-1402", "Unauthorized" ),
    LJ1403("LJ-1403", "Cannot access this resource")
}