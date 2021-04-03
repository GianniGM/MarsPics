package com.giannig.marsexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.giannig.marsexplorer.ui.theme.MarsExplorerTheme
import com.google.accompanist.glide.GlideImage

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())
            .get(MarsPicturesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRoverImages()
        setContent {
            MarsExplorerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ShowMarsImages(viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarsExplorerTheme {
        UpdateStateView(
            UIState.ShowList(
                listOf(
                    UIImage(
                        "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia24466-3000.jpg",
                        "curiosity"
                    )
                )
            )
        )
    }
}

@Composable
fun ShowMarsImages(viewModel: MarsPicturesViewModel) {
    val marsImagesList: UIState by viewModel.roverImagesLiveData.observeAsState(UIState.Loading)
    UpdateStateView(roversImagesData = marsImagesList) //<-Add here click listener
}

@Composable
fun UpdateStateView(roversImagesData: UIState) {
    when (roversImagesData) {
        is UIState.Error -> Text(text = roversImagesData.errorMessage, color = Color.White)
        UIState.Loading -> LoadingState()
        is UIState.ShowList -> RoverImagesList(roversImagesData)
    }
}

@Composable
private fun LoadingState() {
    Box {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun RoverImagesList(imageList: UIState.ShowList) {
    LazyColumn(
        modifier = Modifier.padding(PaddingValues(23.dp)),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        imageList.imageList.forEach {
            item {
                GlideImage(
                    data = it.url,
                    contentDescription = it.roverName,
                    loading = {
                        Box(Modifier.matchParentSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
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


