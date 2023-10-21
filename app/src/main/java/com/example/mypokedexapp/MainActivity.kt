package com.example.mypokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mypokedexapp.model.Pokemon
import com.example.mypokedexapp.ui.theme.MyPokedexAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = "Hello World!")
                }
            }
        }
    }
}

@Composable
fun PokemonListScreen(pokemonList: List<Pokemon>, onItemClick: (Pokemon) -> Unit) {
    LazyColumn {
        items(pokemonList) { pokemon ->
            // Display each Pokémon in the list
            PokemonListItem(pokemon, onItemClick)
        }
    }
}

@Composable
fun PokemonListItem(pokemon: Pokemon, onItemClick: (Pokemon) -> Unit) {
    // Composable to display individual Pokémon items
    Text(text = pokemon.name)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyPokedexAppTheme {
        PokemonListItem(Pokemon("Pikachu")) { }
    }
}