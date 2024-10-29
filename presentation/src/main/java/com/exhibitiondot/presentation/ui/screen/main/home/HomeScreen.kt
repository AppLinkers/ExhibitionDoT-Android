package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    moveEventDetail: (Long) -> Unit,
    moveMy: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

}