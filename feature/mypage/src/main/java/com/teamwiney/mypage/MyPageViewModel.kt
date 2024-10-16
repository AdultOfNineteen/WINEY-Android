package com.teamwiney.mypage

import android.app.Activity
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.core.common.util.Constants.DEVICE_ID
import com.teamwiney.core.common.util.Constants.REFRESH_TOKEN
import com.teamwiney.core.common.util.Constants.USER_ID
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.winebadge.WineBadgeRepository
import com.teamwiney.data.repository.winegrade.WineGradeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val wineGradeRepository: WineGradeRepository,
    private val wineBadgeRepository: WineBadgeRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.Effect>(
    initialState = MyPageContract.State()
) {

    override fun reduceState(event: MyPageContract.Event) {
        viewModelScope.launch {
            when (event) {
                is MyPageContract.Event.ShowWineGradeStandardDialog -> {
                    updateState(currentState.copy(isWineGradeStandardDialogOpen = true))
                }

                is MyPageContract.Event.CloseWineGradeStandardDialog -> {
                    updateState(currentState.copy(isWineGradeStandardDialogOpen = false))
                }

                is MyPageContract.Event.SelectReason -> {
                    postEffect(
                        MyPageContract.Effect.ShowBottomSheet(
                            MyPageContract.BottomSheet.SelectWithdrawalReason
                        )
                    )
                }

                is MyPageContract.Event.LogOut -> {
                    postEffect(
                        MyPageContract.Effect.ShowBottomSheet(
                            MyPageContract.BottomSheet.LogOut
                        )
                    )
                }
            }
        }
    }

    fun getUserNickname() = viewModelScope.launch {
        authRepository.getUserNickname().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collect {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val result = it.data.result

                    updateState(
                        currentState.copy(
                            nickname = result.nickname,
                            newNickname = result.nickname
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun getUserWineGrade() = viewModelScope.launch {
        val userId = runBlocking { dataStoreRepository.getIntValue(USER_ID).first() }

        wineGradeRepository.getUserWineGrade("$userId").onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val result = it.data.result

                    updateState(
                        currentState.copy(
                            currentGrade = result.currentGrade,
                            expectedMonthGrade = result.expectedMonthGrade,
                            noteCount = result.threeMonthsNoteCount
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun getWineGradeStandard() = viewModelScope.launch {
        wineGradeRepository.getWineGradeStandard().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            wineGradeStandard = it.data.result
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun getUserWineBadgeList() = viewModelScope.launch {
        val userId = runBlocking { dataStoreRepository.getIntValue(USER_ID).first() }

        wineBadgeRepository.getUserWineBadgeList(userId.toLong()).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val result = it.data.result

                    updateState(
                        currentState.copy(
                            sommelierBadges = result.sommelierBadgeList,
                            activityBadges = result.activityBadgeList
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun getWineBadgeDetail(
        wineBadgeId: Long
    ) = viewModelScope.launch {
        val userId = runBlocking { dataStoreRepository.getIntValue(USER_ID).first() }

        wineBadgeRepository.getWineBadgeDetail(
            userId = userId.toLong(),
            wineBadgeId = wineBadgeId
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    postEffect(
                        MyPageContract.Effect.ShowBottomSheet(
                            MyPageContract.BottomSheet.WineBadgeDetail(it.data.result)
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun withdrawal(activity: Activity) = viewModelScope.launch {
        val userId = runBlocking { dataStoreRepository.getIntValue(USER_ID).first() }
        val reason = if (currentState.isWithdrawalReasonDirectInput) {
            currentState.withdrawalReasonDirectInput
        } else {
            currentState.withdrawalReason
        }

        authRepository.deleteUser("$userId", reason).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val googleSignInClient = GoogleSignIn.getClient(
                        activity,
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    )
                    googleSignInClient.signOut()

                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            postEffect(
                                MyPageContract.Effect.ShowSnackBar("${error.message}")
                            )
                        }
                    }

                    dataStoreRepository.deleteStringValue(ACCESS_TOKEN)
                    dataStoreRepository.deleteStringValue(REFRESH_TOKEN)
                    postEffect(
                        MyPageContract.Effect.ShowBottomSheet(
                            MyPageContract.BottomSheet.WithdrawalComplete
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun modifyNickname() = viewModelScope.launch {
        authRepository.modifyUserNickname(
            currentState.newNickname
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collect {
            updateState(currentState.copy(isLoading = false))

            when (it) {
                is ApiResult.Success -> {
                    postEffect(
                        MyPageContract.Effect.NavigateTo(
                            destination = MyPageDestinations.MY_PAGE,
                            navOptions = navOptions {
                                popUpTo(MyPageDestinations.MY_PAGE) {
                                    inclusive = true
                                }
                            }
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun logOut(activity: Activity) = viewModelScope.launch {
        val deviceId = runBlocking { dataStoreRepository.getStringValue(DEVICE_ID).first() }

        authRepository.logOut(deviceId).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val googleSignInClient = GoogleSignIn.getClient(
                        activity,
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    )
                    googleSignInClient.signOut()

                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            postEffect(
                                MyPageContract.Effect.ShowSnackBar("${error.message}")
                            )
                        }
                    }

                    postEffect(MyPageContract.Effect.NavigateTo(
                        AuthDestinations.Login.ROUTE, navOptions = navOptions {
                            popUpTo(HomeDestinations.ROUTE) {
                                inclusive = true
                            }
                        }
                    ))
                    dataStoreRepository.deleteStringValue(ACCESS_TOKEN)
                    dataStoreRepository.deleteStringValue(REFRESH_TOKEN)
                }

                is ApiResult.ApiError -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(MyPageContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun updateWithdrawalReason(reason: String) = viewModelScope.launch {
        updateState(
            currentState.copy(
                withdrawalReason = reason,
                isWithdrawalReasonDirectInput = reason == "기타"
            )
        )
    }

    fun updateWithdrawalReasonDirectInput(reason: String) = viewModelScope.launch {
        updateState(
            currentState.copy(
                withdrawalReasonDirectInput = reason
            )
        )
    }

    fun updateNewNickname(nickname: String) = viewModelScope.launch {
        updateState(
            currentState.copy(
                newNickname = nickname
            )
        )
    }
}