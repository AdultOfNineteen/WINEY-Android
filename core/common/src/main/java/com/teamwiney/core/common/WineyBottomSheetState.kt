@file:OptIn(ExperimentalMaterialApi::class)

package com.teamwiney.core.common

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.teamwiney.core.common.`typealias`.SheetContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberWineyBottomSheetState(
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    scope: CoroutineScope = rememberCoroutineScope(),
): WineyBottomSheetState {

    val bottomSheetContent: MutableState<SheetContent?> = remember {
        mutableStateOf(null)
    }
    val onHideBottomSheet = remember {
        mutableStateOf<() -> Unit>({})
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                onHideBottomSheet.value()
            }
            true
        },
        skipHalfExpanded = true
    )

    return remember(bottomSheetState, bottomSheetContent, onHideBottomSheet) {
        WineyBottomSheetState(
            bottomSheetState = bottomSheetState,
            bottomSheetContent = bottomSheetContent,
            keyboardController = keyboardController,
            setOnHideBottomSheet = { onHideBottomSheet.value = it },
            scope = scope
        )
    }
}

@Stable
class WineyBottomSheetState(
    val bottomSheetContent: MutableState<SheetContent?>,
    val bottomSheetState: ModalBottomSheetState,
    val keyboardController: SoftwareKeyboardController?,
    val scope: CoroutineScope,
    val setOnHideBottomSheet: (() -> Unit) -> Unit
) {

    val setBottomSheet: (SheetContent) -> Unit = { content: SheetContent ->
        bottomSheetContent.value = content
    }
    val clearBottomSheet: () -> Unit = {
        bottomSheetContent.value = null
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun showBottomSheet(content: SheetContent) = scope.launch {
        keyboardController?.hide()
        bottomSheetState.hide()
        setBottomSheet(content)
        bottomSheetState.show()
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun hideBottomSheet() = scope.launch {
        keyboardController?.hide()
        clearBottomSheet()
        bottomSheetState.hide()
    }
}