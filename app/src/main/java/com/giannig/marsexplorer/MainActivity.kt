package com.giannig.marsexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        viewModel.roverImagesLiveData.observe(this) {
            setContent {
                MarsExplorerTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        Greeting(it)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarsExplorerTheme {
        Greeting(RoversImagesData.Error("banana"))
    }
}

@Composable
fun Greeting(roversImagesData: RoversImagesData) {
    when (roversImagesData) {
        RoversImagesData.EmptyData -> Text(text = "No Objects here", color = Color.White)
        is RoversImagesData.Error -> Text(text = roversImagesData.errorMessage, color = Color.White)
        RoversImagesData.Loading -> {
            Box {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is RoversImagesData.ShowImage -> RoverImagesList(roversImagesData)
    }
}

@Composable
fun RoverImagesList(roversImagesData: RoversImagesData.ShowImage) {
    LazyColumn(
        modifier = Modifier.padding(PaddingValues(23.dp)),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        roversImagesData.roversImages.forEach {
            item {
                GlideImage(
                    data = it.img_src.replace("http://", "https://"), /*<- WTF NASA!!! you accept only http but you provide pics with http 😂 */
                    contentDescription = it.earth_date,
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


