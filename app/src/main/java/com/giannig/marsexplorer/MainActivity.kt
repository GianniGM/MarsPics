package com.giannig.marsexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.giannig.marsexplorer.api.SpaceRover
import com.giannig.marsexplorer.api.roverDto.PhotoDto
import com.giannig.marsexplorer.mocks.generatePhotoList
import com.giannig.marsexplorer.ui.theme.MarsExplorerTheme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory()).get(MarsPicturesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPicturesFrom(selectedRover)

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

    //temporary approac need to implement a filter
    companion object{
        val selectedRover = SpaceRover.PERSEVERANCE
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarsExplorerTheme {
        MainPage(RoversImagesData.Loading)
    }
}

@Preview(showBackground = true)
@Composable
fun ViewWithFewImages() {
    val listOfPhotos = generatePhotoList(2)
    val roverName = "Banana"
    MarsExplorerTheme {
        MainPage(
            roversImagesData = RoversImagesData.ShowImage(listOfPhotos, roverName),
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ViewWithLotOfImages() {
    val listOfPhotos = generatePhotoList(12)
    val roverName = "Banana"
    MarsExplorerTheme {
        MainPage(
            roversImagesData = RoversImagesData.ShowImage(listOfPhotos, roverName),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewEmpty() {
    val listOfPhotos = emptyList<PhotoDto>()
    val roverName = "Banana"
    MarsExplorerTheme {
        MainPage(
            roversImagesData = RoversImagesData.ShowImage(listOfPhotos, roverName),
        )
    }
}


@Preview(showBackground = false)
@Composable
fun ListItem() {
    val urlString = "https://www.nasa.gov/sites/default/files/styles/image_card_4x3_ratio/public/thumbnails/image/pia24269-3-16.jpg"
    val urlName = "Mars is nice"
    MarsSingleItem(
        urlString,
        urlName,
    )
}


@Composable
fun MainPage(roversImagesData: RoversImagesData) {
    val rover = MainActivity.selectedRover
    Column {
        TopAppBar(
            title = {
                Text(text = "Pictures from ${rover.roverName()}")
            },
        )
        MarsPictures(roversImagesData)
    }
}


@Composable
private fun MarsPictures(roversImagesData: RoversImagesData) = when (roversImagesData) {
    RoversImagesData.EmptyData -> Text(text = "No Objects here", color = Color.White)
    is RoversImagesData.Error -> Text(text = roversImagesData.errorMessage, color = Color.Blue)
    RoversImagesData.Loading -> SetUpLoadingView()
    is RoversImagesData.ShowImage -> RoverImagesList(roversImagesData)
}

@Composable
private fun SetUpLoadingView() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        //todo: nice to have a better view of the circular progress indicator
        CircularProgressIndicator(
            color = Color.Blue,
            modifier = Modifier.padding(23.dp)
        )
    }
}

@Composable
fun RoverImagesList(roversImagesData: RoversImagesData.ShowImage) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        roversImagesData.roversImages.forEach { photoDto ->
            item {
                MarsSingleItem(photoDto.img_src, photoDto.earth_date)
            }
        }
    }
}

@Composable
fun MarsSingleItem(imageSource: String, earthDate: String) {
    val shape = AbsoluteRoundedCornerShape(16.dp)
    AsyncImage(
        placeholder = painterResource(id = R.drawable.mars),
        model = imageSource,
        contentDescription = earthDate,
        modifier = Modifier
            .padding(24.dp, 16.dp)
            .border(1.dp, Color.Black, shape)
            .border(2.dp, Color.Yellow, shape)
            .border(3.dp, Color.Black, shape)
            .clip(shape),
    )
}




