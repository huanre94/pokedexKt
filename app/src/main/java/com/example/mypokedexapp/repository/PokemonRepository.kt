package com.example.mypokedexapp.repository

import com.example.mypokedexapp.data.remote.PokeApi
import com.example.mypokedexapp.data.remote.responses.Pokemon
import com.example.mypokedexapp.data.remote.responses.PokemonList
import com.example.mypokedexapp.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }

        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(id: Int): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }

        return Resource.Success(response)
    }
}