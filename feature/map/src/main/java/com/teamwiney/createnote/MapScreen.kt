@file:OptIn(ExperimentalNaverMapApi::class)

package com.teamwiney.createnote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

@Composable
fun MapScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            modifier = Modifier.fillMaxSize()
        )
    }
}