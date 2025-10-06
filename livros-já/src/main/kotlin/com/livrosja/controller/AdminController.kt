package com.livrosja.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("admin")
class AdminController(

) {

    @GetMapping("/report") // retorna todos os usuários ou retorna um/vários pelas suas letras
    fun report(): String{
       return "Isso é um report, apenas admins podem visualizar isso"
    }

}


