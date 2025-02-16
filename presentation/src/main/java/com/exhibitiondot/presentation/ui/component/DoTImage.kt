package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.exhibitiondot.domain.model.ImageSource
import com.exhibitiondot.presentation.R

@Composable
fun DoTImage(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.image_placeholder),
        contentScale = contentScale,
        contentDescription = url
    )
}

@Composable
fun PostEventImage(
    modifier: Modifier = Modifier,
    image: ImageSource?,
    addImage: () -> Unit,
    deleteImage: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(312.dp)
            .height(372.dp)
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(360.dp)
                .align(Alignment.BottomStart)
        ) {
            image?.let {
                DoTImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    url = when (it) {
                        is ImageSource.Local -> it.file.absolutePath
                        is ImageSource.Remote -> it.url
                    },
                    contentScale = ContentScale.FillBounds
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .clickable(onClick = addImage),
                    contentAlignment = Alignment.Center
                ) {
                    AddIcon(
                        modifier = Modifier.size(36.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        if (image != null) {
            XCircle(
                modifier = Modifier.align(Alignment.TopEnd),
                size = 24,
                onClick = deleteImage
            )
        }
    }
}