package com.example.architectcoderspracticauno.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.architectcoderspracticauno.R
import com.example.architectcoderspracticauno.data.model.Wizard
import com.example.architectcoderspracticauno.ui.common.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = viewModel()
) {
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        vm.onUiReady("gryffindor")
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Harry Potter API") },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = ({ HomeBottomBar() })
        ) {padding ->
            val state = vm.state

            if (state.loading){
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = padding
            ) {
                items(state.wizards, key = { it.id }){wizard ->
                    WizardItem(wizard)
                }
            }
        }
    }
}

@Composable
fun HomeBottomBar() {
    BottomAppBar(
        modifier = Modifier
            .background(Color.Blue),
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(
                onClick = { /* Handle navigation icon click */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gryffindor),
                    contentDescription = "Gryffindor",
                    tint = Color.Unspecified
                )
            }
            IconButton(
                onClick = { /* Handle navigation icon click */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_slytherin),
                    contentDescription = "Slytherin",
                    tint = Color.Unspecified
                )
            }

            IconButton(
                onClick = { /* Handle navigation icon click */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ravenclaw),
                    contentDescription = "Ravenclaw",
                    tint = Color.Unspecified
                )
            }

            IconButton(
                onClick = { /* Handle navigation icon click */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hufflepuff),
                    contentDescription = "Hufflepuff",
                    tint = Color.Unspecified
                )
            }
        }

    }
}

@Composable
fun WizardItem(wizard: Wizard) {
    Column {
        if (wizard.image != "")
            LoadImageFromInternet(wizard)
        else
            LoadImageFromLocal(wizard)

        Text(
            text = wizard.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun LoadImageFromInternet(wizard: Wizard) {
    AsyncImage(
        model = wizard.image,
        contentDescription = wizard.name,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2 / 3f)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun LoadImageFromLocal(wizard: Wizard) {
    Image(
        painter = painterResource(id = R.drawable.im_placeholder),
        contentDescription = wizard.name,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2 / 3f)
            .clip(MaterialTheme.shapes.small)
    )
}

@Preview
@Composable
private fun preview() {
    HomeBottomBar()
}