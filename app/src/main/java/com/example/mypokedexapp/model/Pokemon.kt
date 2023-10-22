package com.example.mypokedexapp.model

data class Pokemon(
    val id: Int = 0,
    val name: String = "",
    val imgUri: String ,

    val typeOfPokemon: List<String>? = listOf(),


    var sprites: List<String>? = listOf(),

    val abilites: List<String>? = listOf(),

    val weight: Int,
)