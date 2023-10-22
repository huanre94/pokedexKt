package com.example.mypokedexapp.utils

import androidx.compose.ui.graphics.Color
import com.example.mypokedexapp.data.remote.responses.Type
import com.example.mypokedexapp.ui.theme.TypeBug
import com.example.mypokedexapp.ui.theme.TypeDark
import com.example.mypokedexapp.ui.theme.TypeDragon
import com.example.mypokedexapp.ui.theme.TypeElectric
import com.example.mypokedexapp.ui.theme.TypeFairy
import com.example.mypokedexapp.ui.theme.TypeFighting
import com.example.mypokedexapp.ui.theme.TypeFire
import com.example.mypokedexapp.ui.theme.TypeFlying
import com.example.mypokedexapp.ui.theme.TypeGhost
import com.example.mypokedexapp.ui.theme.TypeGrass
import com.example.mypokedexapp.ui.theme.TypeGround
import com.example.mypokedexapp.ui.theme.TypeIce
import com.example.mypokedexapp.ui.theme.TypeNormal
import com.example.mypokedexapp.ui.theme.TypePoison
import com.example.mypokedexapp.ui.theme.TypePsychic
import com.example.mypokedexapp.ui.theme.TypeRock
import com.example.mypokedexapp.ui.theme.TypeSteel
import com.example.mypokedexapp.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.White
    }
}