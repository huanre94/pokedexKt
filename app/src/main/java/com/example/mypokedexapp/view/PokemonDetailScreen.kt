package com.example.mypokedexapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mypokedexapp.data.remote.responses.Ability
import com.example.mypokedexapp.data.remote.responses.Move
import com.example.mypokedexapp.data.remote.responses.Pokemon
import com.example.mypokedexapp.data.remote.responses.Sprites
import com.example.mypokedexapp.data.remote.responses.Type
import com.example.mypokedexapp.utils.Resource
import com.example.mypokedexapp.utils.parseTypeToColor
import com.example.mypokedexapp.viewmodel.PokemonDetailViewModel
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonId: Int,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonId)
    }.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopStart)
        )
        PokemonDetailStateWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
            if (pokemonInfo is Resource.Success) {
                pokemonInfo.data?.sprites?.let { url ->
                    AsyncImage(
                        model = url.front_default,
                        contentDescription = pokemonInfo.data.name,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopCenter, modifier = modifier.background(
            Brush.verticalGradient(
                listOf(Color.Black, Color.Transparent)
            )
        )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            PokemonDetailSection(
                pokemonInfo = pokemonInfo.data!!,
                modifier = modifier.offset(x = (-20).dp)
            )
        }

        is Resource.Error -> {
            Text(
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokemonInfo.id} ${pokemonInfo.name.capitalize(Locale.ROOT)}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        PokemonTypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )
        PokemonAbilitiesSection(abilities = pokemonInfo.abilities)
        PokemonSprites(pokemonSprites = pokemonInfo.sprites)
        PokemonMoves(moves = pokemonInfo.moves)
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp),
                content = {
                    Text(
                        text = type.type.name,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            )
        }
    }
}


@Composable
fun PokemonAbilitiesSection(abilities: List<Ability>) {
    Text(
        text = "Habilidades",
        modifier = Modifier.padding(8.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(4.dp))
        for (ability in abilities) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(Color.LightGray)
                    .clip(CircleShape)
                    .height(35.dp),
                content = {
                    Text(
                        text = ability.ability.name,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            )
        }
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float, dataUnit: String, dataIcon: Painter, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue$dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PokemonDetailDataSection(pokemonWeight: Int, pokemonHeight: Int, sectionHeight: Dp = 80.dp) {
    val pokemonWeightInKg = remember {
        (pokemonWeight * 100f).roundToInt() / 1000f
    }
    val pokemonHeightInMeters = remember {
        (pokemonHeight * 100f).roundToInt() / 1000f
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "Kg",
            dataIcon = painterResource(id = androidx.core.R.drawable.ic_call_decline),
            modifier = Modifier.weight(1f)
        )
//        Spacer(modifier = Modifier
//            .size(1.dp, sectionHeight)
//            .background(Color.LightGray))
//        PokemonDetailDataItem(
//            dataValue = pokemonHeightInMeters,
//            dataUnit = "m",
//            dataIcon = painterResource(id = androidx.core.R.drawable.ic_call_answer),
//            modifier = Modifier.weight(1f)
//        )
    }
}

@Composable
fun PokemonSprites(pokemonSprites: Sprites) {
    Text(
        text = "Espiritus",
        modifier = Modifier.padding(8.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(4.dp))
    //Row to show all sprites


    Row(modifier = Modifier.fillMaxWidth()) {
        //filter all sprites that have the word front in them from pokemonSprites

        AsyncImage(
            model = pokemonSprites.front_default,
            contentDescription = "Pokemon Sprite",
            modifier = Modifier
                .size(100.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            model = pokemonSprites.back_default,
            contentDescription = "Pokemon Sprite",
            modifier = Modifier
                .size(100.dp)
                .weight(1f)
        )
    }
}

@Composable
fun PokemonMoves(moves: List<Move>) {
    Text(
        text = "Movimientos",
        modifier = Modifier.padding(8.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(4.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        moves.forEach { move ->
            Text(
                text = move.move.name.replace("-", " ").capitalize(Locale.ROOT),
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}

