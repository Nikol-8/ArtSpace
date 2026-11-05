package com.nikol.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikol.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Molde con los datos de una obra
data class Obra(
    @DrawableRes val imagen: Int,
    @StringRes val titulo: Int,
    @StringRes val artista: Int,
    @StringRes val anio: Int,
    @StringRes val descripcion: Int
)

//Composable padre con las 3 cajas
@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    // ESTADO: número de la obra que se está viendo (de 1 a 6)
    var obraActual by remember { mutableStateOf(1) }

    // Según el número, elegimos la ficha de la obra
    val obra = when (obraActual) {
        1 -> Obra(R.drawable._1, R.string.obra_1_titulo, R.string.obra_1_artista, R.string.obra_1_anio, R.string.obra_1_descripcion)
        2 -> Obra(R.drawable._2, R.string.obra_2_titulo, R.string.obra_2_artista, R.string.obra_2_anio, R.string.obra_2_descripcion)
        3 -> Obra(R.drawable._3, R.string.obra_3_titulo, R.string.obra_3_artista, R.string.obra_3_anio, R.string.obra_3_descripcion)
        4 -> Obra(R.drawable._4, R.string.obra_4_titulo, R.string.obra_4_artista, R.string.obra_4_anio, R.string.obra_4_descripcion)
        5 -> Obra(R.drawable._5, R.string.obra_5_titulo, R.string.obra_5_artista, R.string.obra_5_anio, R.string.obra_5_descripcion)
        else -> Obra(R.drawable._6, R.string.obra_6_titulo, R.string.obra_6_artista, R.string.obra_6_anio, R.string.obra_6_descripcion)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtworkWall(
            imagen = obra.imagen,
            descripcion = obra.descripcion,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        ArtworkDescriptor(
            titulo = obra.titulo,
            artista = obra.artista,
            anio = obra.anio
        )
        Spacer(modifier = Modifier.height(16.dp))
        DisplayController(
            onPrevious = {
                obraActual = if (obraActual == 1) 6 else obraActual - 1
            },
            onNext = {
                obraActual = if (obraActual == 6) 1 else obraActual + 1
            }
        )
    }
}

//Caja 1 pared (imagen y marco)
@Composable
fun ArtworkWall(
    @DrawableRes imagen: Int,
    @StringRes descripcion: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(imagen),
            contentDescription = stringResource(descripcion),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        )
    }
}

//Caja 2 (titulo y artista)
@Composable
fun ArtworkDescriptor(
    @StringRes titulo: Int,
    @StringRes artista: Int,
    @StringRes anio: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFECEBF4))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(titulo),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "${stringResource(artista)} (${stringResource(anio)})",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

//Caja 3 (botones)
@Composable
fun DisplayController(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPrevious, modifier = Modifier.width(140.dp)) {
            Text(text = stringResource(R.string.boton_anterior))
        }
        Button(onClick = onNext, modifier = Modifier.width(140.dp)) {
            Text(text = stringResource(R.string.boton_siguiente))
        }
    }
}