package com.exhibitiondot.presentation.ui.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.state.IEditTextState

@Composable
fun getActivityWindow(): Window? = LocalView.current.context.getActivityWindow()

private tailrec fun Context.getActivityWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.getActivityWindow()
        else -> null
    }

@Composable
fun HomeSearchDialog(
    modifier: Modifier = Modifier,
    queryState: IEditTextState,
    applyQuery: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = false
        ),
        onDismissRequest = onDismissRequest
    ) {
        val activityWindow = getActivityWindow()
        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
        val parentView = LocalView.current.parent as View
        val focusRequester = remember { FocusRequester() }
        SideEffect {
            if (activityWindow != null && dialogWindow != null) {
                val attributes = WindowManager.LayoutParams().apply {
                    copyFrom(activityWindow.attributes)
                    type = dialogWindow.attributes.type
                }
                dialogWindow.attributes = attributes
                parentView.layoutParams = FrameLayout.LayoutParams(
                    activityWindow.decorView.width,
                    activityWindow.decorView.height
                )
            }
        }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            HomeSearchBar(
                queryState = queryState,
                applyQuery = applyQuery,
                focusRequester = focusRequester,
                onBack = onDismissRequest
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SearchIcon(
                        size = 56,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    DoTSpacer(size = 16)
                    Text(
                        text = stringResource(R.string.home_search_announce),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}