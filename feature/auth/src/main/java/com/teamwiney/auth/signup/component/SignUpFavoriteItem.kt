package com.teamwiney.auth.signup.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.auth.signup.component.state.SignUpFavoriteItemUiState
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun RowScope.SignUpFavoriteItem(
    signUpFavoriteItemUiState: SignUpFavoriteItemUiState,
    updateSignUpFavoriteItemUiState: (SignUpFavoriteItemUiState) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(0.9f)
            .clickable {
                updateSignUpFavoriteItemUiState(signUpFavoriteItemUiState)
            }
            .border(
                border = BorderStroke(
                    1.dp,
                    if (signUpFavoriteItemUiState.isSelected) WineyTheme.colors.main_2 else WineyTheme.colors.gray_800
                ),
                shape = RoundedCornerShape(11.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = signUpFavoriteItemUiState.description,
            color = WineyTheme.colors.gray_700,
            style = WineyTheme.typography.captionM1,
            textAlign = TextAlign.Center
        )
        HeightSpacer(6.dp)
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = signUpFavoriteItemUiState.title,
            color = if (signUpFavoriteItemUiState.isSelected) WineyTheme.colors.main_2 else WineyTheme.colors.gray_500,
            style = WineyTheme.typography.bodyB2,
            textAlign = TextAlign.Center
        )
    }
}