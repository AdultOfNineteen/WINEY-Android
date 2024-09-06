import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.notedetail.notelist.NoteListContract
import com.teamwiney.notedetail.notelist.NoteListViewModel
import com.teamwiney.ui.components.LoadingDialog
import com.teamwiney.ui.components.NoteReviewItem
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteListScreen(
    appState: WineyAppState,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val listState = rememberLazyListState()

    // 스크롤이 끝에 도달했을 때 처리하는 효과
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

                if (lastVisibleItemIndex == layoutInfo.totalItemsCount - 1 && !uiState.isLastPage && !uiState.isLoading) {
                    viewModel.processEvent(NoteListContract.Event.FetchMoreNotes)
                }
            }
    }

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is NoteListContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
    ) {
        TopBar(
            content = "테이스팅 노트 모음",
            leadingIconOnClick = {
                appState.navController.navigateUp()
            }
        )
        Text(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 20.dp,
            ),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = WineyTheme.colors.main_3
                    )
                ) {
                    append("${uiState.tastingNotesTotalCount}개")
                }
                append("의 테이스팅 노트가 있어요!")
            },
            style = WineyTheme.typography.title2.copy(
                color = WineyTheme.colors.gray_50
            ),
        )

        if (uiState.isLoading && uiState.currentPage == 0) {
            LoadingDialog()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WineyTheme.colors.background_1),
                state = listState
            ) {
                items(uiState.tastingNotes) { note ->
                    NoteReviewItem(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        nickName = note.userNickname,
                        date = note.noteDate,
                        rating = note.starRating,
                        buyAgain = note.buyAgain,
                        navigateToNoteDetail = {
                            appState.navController.navigate("${NoteDestinations.NOTE_DETAIL}?id=${note.id}")
                        }
                    )
                }
            }
        }
    }
}