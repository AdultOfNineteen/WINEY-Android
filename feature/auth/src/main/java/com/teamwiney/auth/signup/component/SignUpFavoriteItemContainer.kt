package com.teamwiney.auth.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamwiney.auth.signup.component.state.SignUpFavoriteCategoryUiState
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun SignUpFavoriteItemContainer(
    signUpFavoriteCategoryUiState: SignUpFavoriteCategoryUiState,
    updateSignUpFavoriteItemUiState: (SignUpFavoriteCategoryUiState) -> Unit,
    nextStep: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxWidth()

    ) {
        Text(
            text = signUpFavoriteCategoryUiState.title,
            color = Color.White,
            style = WineyTheme.typography.captionB1,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(50.dp))
                .background(WineyTheme.colors.main_1)
                .padding(horizontal = 22.dp, vertical = 11.dp)
        )
        HeightSpacer(31.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            signUpFavoriteCategoryUiState.signUpFavoriteItem.forEach {
                SignUpFavoriteItem(
                    signUpFavoriteItemUiState = it,
                    updateSignUpFavoriteItemUiState = { selectedItem ->
                        updateSignUpFavoriteItemUiState(
                            signUpFavoriteCategoryUiState.copy(
                                signUpFavoriteItem = signUpFavoriteCategoryUiState.signUpFavoriteItem.map { signUpFavoriteItemUiState ->
                                    if (signUpFavoriteItemUiState.title == selectedItem.title) {
                                        it.copy(isSelected = !selectedItem.isSelected)
                                    } else {
                                        signUpFavoriteItemUiState.copy(isSelected = false)
                                    }
                                }
                            )
                        )
                        if (!selectedItem.isSelected) nextStep()
                    }
                )
            }
        }
    }
}