package com.teamwiney.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun WButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Button",
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    border: BorderStroke? = null,
    enableTextColor: Color = Color.White,
    disableTextColor: Color = WineyTheme.colors.gray_600,
    enableBackgroundColor: Color = WineyTheme.colors.main_1,
    disableBackgroundColor: Color = WineyTheme.colors.gray_900,
    enableTextStyle: TextStyle = WineyTheme.typography.headline,
    disableTextStyle: TextStyle = WineyTheme.typography.headline,
    shape: RoundedCornerShape = RoundedCornerShape(5.dp),
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = enableBackgroundColor,
            disabledContainerColor = disableBackgroundColor,
        ),
        contentPadding = contentPadding,
        shape = shape,
        enabled = enabled
    ) {
        Text(
            text = text,
            color = if (enabled) enableTextColor else disableTextColor,
            style = if (enabled) enableTextStyle else disableTextStyle,
        )
    }
}
