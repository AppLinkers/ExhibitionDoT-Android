package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.R

enum class SupportingTextType {
    NONE, INFO, ERROR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoTTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String,
    supportingText: String? = null,
    enabled: Boolean = true,
    supportingTextType: SupportingTextType = SupportingTextType.INFO,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit,
    onResetValue: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = value,
            enabled = enabled,
            textStyle = MaterialTheme.typography.labelLarge,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            onValueChange = onValueChange
        ) {
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = it,
                enabled = enabled,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    Text(
                        text = placeHolder,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
                },
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        XCircle(size = 20, onClick = onResetValue)
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            )
        }
        supportingText?.let {
            DoTSpacer(size = 8)
            Text(
                text = it,
                style = MaterialTheme.typography.displayMedium,
                color = when (supportingTextType) {
                    SupportingTextType.INFO -> MaterialTheme.colorScheme.onSurface
                    SupportingTextType.ERROR -> MaterialTheme.colorScheme.onError
                    SupportingTextType.NONE -> Color.Transparent
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    onResetValue: () -> Unit,
    onSearch: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        enabled = true,
        maxLines = 1,
        textStyle = MaterialTheme.typography.displayMedium,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        onValueChange = onValueChange
    ) {
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            shape = CircleShape,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.home_search_placeholder),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    XCircle(size = 20, onClick = onResetValue)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),
            contentPadding = PaddingValues(all = 16.dp)
        )
    }
}