package com.teamwiney.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun HintPopUp(
    offset: IntOffset = IntOffset(0, 0),
    text: String = "나에게 맞는 와인을 분석해줘요!"
) {
    val density = LocalDensity.current
    val popupOffset = IntOffset(
        x = offset.x,
        y = offset.y + density.run { 5.dp.roundToPx() }
    )

    Popup(
        alignment = Alignment.BottomEnd,
        offset = popupOffset
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Row {
                Spacer(
                    modifier = Modifier
                        .size(5.dp)
                        .background(
                            color = WineyTheme.colors.main_2,
                            shape = TriangleShape()
                        )
                )

                Spacer(modifier = Modifier.width(17.dp))
            }


            Surface(
                shape = RoundedCornerShape(15.dp),
                color = WineyTheme.colors.main_2
            ) {
                Text(
                    modifier = Modifier.padding(
                        vertical = 4.dp,
                        horizontal = 8.dp
                    ),
                    text = text,
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_100
                )
            }
        }
    }
}

class TriangleShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            // Add line to bottom right corner
            lineTo(size.width, size.height)
            // Add line to bottom left corner
            lineTo(0f, size.height)
        }

        path.close()

        return Outline.Generic(path = path)
    }

}