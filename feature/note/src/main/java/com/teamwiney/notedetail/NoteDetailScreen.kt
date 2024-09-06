package com.teamwiney.notedetail

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.notedetail.component.NoteDeleteBottomSheet
import com.teamwiney.notedetail.component.NoteDetailBottomSheet
import com.teamwiney.notedetail.component.WineInfo
import com.teamwiney.notedetail.component.WineMemo
import com.teamwiney.notedetail.component.WineOrigin
import com.teamwiney.notedetail.component.WineSmellFeature
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.LoadingDialog
import com.teamwiney.ui.components.NoteReviewItem
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.detail.NoteTitleAndDescription
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun NoteDetailScreen(
    appState: WineyAppState,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    bottomSheetState: WineyBottomSheetState
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { uiState.tabs.size })

    val context = LocalContext.current

    BackHandler {
        if (bottomSheetState.bottomSheetState.isVisible) {
            bottomSheetState.hideBottomSheet()
        } else {
            appState.navController.navigateUp()
        }
    }

    LaunchedEffect(true) {
        viewModel.effect.collectLatest { effect ->
            bottomSheetState.hideBottomSheet()
            when (effect) {
                is NoteDetailContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteDetailContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is NoteDetailContract.Effect.ShowBottomSheet -> {
                    bottomSheetState.showBottomSheet {
                        when (effect.bottomSheet) {
                            is NoteDetailContract.BottomSheet.NoteOption -> {
                                NoteDetailBottomSheet(
                                    isShowShareNote = uiState.noteDetail.public,
                                    shareNote = {
                                        bottomSheetState.hideBottomSheet()
                                        shareNoteWithKakaoLink(
                                            context,
                                            "[${uiState.noteDetail.userNickname}] 님의 [${uiState.noteDetail.wineName}] 테이스팅 노트를 확인해보세요!",
                                            uiState.noteDetail.noteId.toInt(),
                                            appState::showSnackbar
                                        )
                                    },
                                    deleteNote = {
                                        bottomSheetState.hideBottomSheet()
                                        viewModel.processEvent(
                                            NoteDetailContract.Event.ShowNoteDeleteBottomSheet
                                        )
                                    },
                                    patchNote = {
                                        bottomSheetState.hideBottomSheet()
                                        appState.navigate("${NoteDestinations.Write.ROUTE}?noteId=${uiState.noteDetail.noteId}")
                                    }
                                )
                            }

                            is NoteDetailContract.BottomSheet.NoteDelete -> {
                                NoteDeleteBottomSheet(
                                    onConfirm = {
                                        viewModel.deleteNote(uiState.noteDetail.noteId.toInt())
                                    },
                                    onCancel = {
                                        bottomSheetState.hideBottomSheet()
                                    }
                                )
                            }
                        }
                    }
                }

                is NoteDetailContract.Effect.NoteDeleted -> {
                    appState.showSnackbar("노트가 삭제되었습니다.")
                    appState.navController.navigateUp()
                }
            }
        }
    }

    if (uiState.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                leadingIconOnClick = {
                    appState.navController.navigateUp()
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_kebab_28),
                        contentDescription = null,
                        tint = WineyTheme.colors.gray_50,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(28.dp)
                            .clickable {
                                viewModel.processEvent(NoteDetailContract.Event.ShowNoteOptionBottomSheet)
                            }
                    )
                }
            )
            LoadingDialog()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            item {
                TopBar(
                    leadingIconOnClick = {
                        appState.navController.navigateUp()
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_kebab_28),
                            contentDescription = null,
                            tint = WineyTheme.colors.gray_50,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(28.dp)
                                .clickable {
                                    viewModel.processEvent(NoteDetailContract.Event.ShowNoteOptionBottomSheet)
                                }
                        )
                    }
                )
            }

            item(key = "header") {
                Column {
                    NoteTitleAndDescription(
                        number = uiState.noteDetail.tastingNoteNo,
                        date = uiState.noteDetail.noteDate,
                        type = uiState.noteDetail.wineType,
                        name = uiState.noteDetail.wineName
                    )

                    HeightSpacerWithLine(
                        modifier = Modifier.padding(vertical = 20.dp),
                        color = WineyTheme.colors.gray_900
                    )

                    WineOrigin(uiState.noteDetail)

                    HeightSpacerWithLine(
                        modifier = Modifier.padding(top = 20.dp),
                        color = WineyTheme.colors.gray_900
                    )
                }
            }

            item(key = "tab") {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    indicator = { },
                    containerColor = WineyTheme.colors.background_1,
                    contentColor = WineyTheme.colors.gray_50,
                    divider = { HorizontalDivider(color = WineyTheme.colors.gray_900) }
                ) {
                    uiState.tabs.forEachIndexed { index, _ ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                appState.scope.launch { pagerState.scrollToPage(index) }
                            },
                            text = {
                                Text(
                                    text = uiState.tabs[index],
                                    style = WineyTheme.typography.bodyM2.copy(
                                        color = if (pagerState.currentPage == index) WineyTheme.colors.gray_50 else WineyTheme.colors.gray_700
                                    )
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState
                ) { page ->
                    when (page) {
                        0 -> MyNoteContent(uiState.noteDetail)
                        1 -> OtherNotesContent(
                            otherNotes = uiState.otherNotes,
                            otherNotesTotalCount = uiState.otherNotesTotalCount,
                            navigateToNoteDetail = { noteId ->
                                appState.navigate("${NoteDestinations.NOTE_DETAIL}?id=$noteId")
                            },
                            onShowMore = {
                                appState.navigate("${NoteDestinations.NOTE_LIST}?id=${uiState.noteDetail.wineId}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MyNoteContent(noteDetail: TastingNoteDetail) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WineSmellFeature(noteDetail)

        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 38.dp, bottom = 30.dp),
            color = WineyTheme.colors.gray_900
        )

        WineInfo(tastingNoteDetail = noteDetail)

        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 25.dp, bottom = 30.dp),
            color = WineyTheme.colors.gray_900
        )

        WineMemo(noteDetail)
    }
}

@Composable
fun OtherNotesContent(
    otherNotes: List<TastingNote>,
    otherNotesTotalCount: Int,
    navigateToNoteDetail: (Int) -> Unit,
    onShowMore: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Other Notes",
            style = WineyTheme.typography.display2,
            color = WineyTheme.colors.gray_50,
        )

        HeightSpacer(height = 20.dp)

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = WineyTheme.colors.main_3
                    )
                ) {
                    append("${otherNotesTotalCount}개")
                }
                append("의 테이스팅 노트가 있어요!")
            },
            style = WineyTheme.typography.title2.copy(
                color = WineyTheme.colors.gray_50
            ),
        )

        HeightSpacer(height = 20.dp)

        otherNotes.forEach {
            NoteReviewItem(
                nickName = it.userNickname,
                date = it.noteDate,
                rating = it.starRating,
                buyAgain = it.buyAgain,
                navigateToNoteDetail = {
                    navigateToNoteDetail(it.id.toInt())
                }
            )
        }

        HeightSpacer(height = 20.dp)

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onShowMore() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "더 보러가기",
                style = WineyTheme.typography.bodyM2.copy(
                    color = WineyTheme.colors.gray_400
                )
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "IC_ARROW_RIGHT",
                tint = Color.Unspecified
            )
        }

        HeightSpacer(height = 33.dp)
    }
}

private fun shareNoteWithKakaoLink(
    context: Context,
    title: String,
    noteId: Int,
    showErrorMessage: (String) -> Unit
) {
    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        ShareClient.instance.shareCustom(
            context,
            111850L,
            templateArgs = mapOf(
                "title" to title,
                "id" to noteId.toString(),
            )
        ) { sharingResult, error ->
            if (error != null) {
                showErrorMessage("카카오톡 공유 실패")
            } else {
                context.startActivity(sharingResult?.intent)
            }
        }
    } else {
        val shareUrl = WebSharerClient.instance.makeCustomUrl(
            111850L,
            templateArgs = mapOf(
                "title" to title,
                "id" to noteId.toString(),
            )
        )

        try {
            KakaoCustomTabsClient.openWithDefault(context, shareUrl)
        } catch (e: Exception) {
            showErrorMessage("카카오톡 공유 실패")
        }

        try {
            KakaoCustomTabsClient.openWithDefault(context, shareUrl)
        } catch (e: Exception) {
            showErrorMessage("카카오톡 공유 실패")
        }
    }
}