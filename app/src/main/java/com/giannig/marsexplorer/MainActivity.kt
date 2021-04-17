package com.giannig.marsexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.giannig.marsexplorer.api.SpaceRovers
import com.giannig.marsexplorer.api.SpaceRovers.*
import com.giannig.marsexplorer.ui.theme.MarsExplorerTheme
import com.google.accompanist.glide.GlideImage

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory()).get(MarsPicturesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPicturesFrom(CURIOSITY)
        setContent {
            val roverImages = viewModel.mutableRoverState.value
            MarsExplorerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainPage(roverImages)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarsExplorerTheme {
        MainPage(RoversImagesData.Loading)
    }
}

@Composable
fun MainPage(roversImagesData: RoversImagesData) {
    Column {
        TopAppBar(
            title = {
                Text(text = "Pictures from ${CURIOSITY.roverName()}")
            },
        )
        MarsPictures(roversImagesData)
    }
}


@Composable
private fun MarsPictures(roversImagesData: RoversImagesData) = when (roversImagesData) {
    RoversImagesData.EmptyData -> Text(text = "No Objects here", color = Color.White)
    is RoversImagesData.Error -> Text(text = roversImagesData.errorMessage, color = Color.Blue)
    RoversImagesData.Loading -> setUpLoadingView()
    is RoversImagesData.ShowImage -> RoverImagesList(roversImagesData)
}

@Composable
private fun setUpLoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = Color.LightGray,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RoverImagesList(roversImagesData: RoversImagesData.ShowImage) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        roversImagesData.roversImages.forEach {
            item {
                GlideImage(
                    data = it.img_src,
                    contentDescription = it.earth_date,
                    loading = {
                        setUpLoadingView()
                    },
                    error = {
                        Image(
                            painter = painterResource(R.drawable.banana),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}



