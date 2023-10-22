package com.example.mypokedexapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.data.remote.responses.Pokemon
import com.example.mypokedexapp.repository.PokemonRepository
import com.example.mypokedexapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(pokemonId: Int): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonId)
    }
}