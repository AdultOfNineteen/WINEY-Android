package com.teamwiney.core_design_system.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.core_design_system.theme.ButtonTextColor
import com.teamwiney.core_design_system.theme.Header1

@Preview(widthDp = 50, heightDp = 50)
@Composable
fun Button(
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    text: String = "Button",
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, ButtonTextColor),
    ) {
        Text(
            text = text,
            style = Header1,
            color = ButtonTextColor,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}
