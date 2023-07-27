package com.teamwiney.core_design_system.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core_design_system.theme.WineyTheme

@Preview
@Composable
fun WButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Button",
    border: BorderStroke? = null,
    enableTextColor: Color = Color.White,
    disableTextColor: Color = WineyTheme.colors.gray_600,
    enableBackgroundColor: Color = WineyTheme.colors.main_1,
    disableBackgroundColor: Color = WineyTheme.colors.gray_900,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) enableBackgroundColor else disableBackgroundColor,
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(
            text = text,
            color = if (enabled) enableTextColor else disableTextColor,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}
