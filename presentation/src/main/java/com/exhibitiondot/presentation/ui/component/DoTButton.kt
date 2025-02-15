package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.theme.buttonHeight

@Composable
fun DoTButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight),
        enabled = enabled && isLoading.not(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = if (isLoading) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.inversePrimary
            },
        ),
        onClick = onClick
    ) {
        if (isLoading.not()) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.background
            )
        } else {
            DoTLoading(
                size = 18,
                color = MaterialTheme.colorScheme.background,
                strokeWidth = 3
            )
        }
    }
}

@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onLogin: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(horizontal = 20.dp)
            .clickable(
                enabled = enabled,
                onClick = onLogin,
            ),
    ) {
        Image(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.kakao_symbol),
            contentDescription = "kakao-symbol"
        )
        if (enabled) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.kakao_login),
                style = MaterialTheme.typography.labelLarge
            )
        } else {
            DoTLoading(
                modifier = Modifier.align(Alignment.Center),
                size = 18,
                color = MaterialTheme.colorScheme.background,
                strokeWidth = 3
            )
        }
    }
}

@Composable
fun HomeSheetButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color,
    contentColor: Color,
    borderColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .width(120.dp)
            .height(48.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor
        )
    }
}

@Composable
fun HomeAddButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        onClick = onClick
    ) {
        AddIcon()
    }
}
