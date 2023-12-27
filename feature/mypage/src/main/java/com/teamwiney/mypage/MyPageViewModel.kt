package com.teamwiney.mypage

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.util.Constants.USER_ID
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.persistence.DataStoreRepository
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
    private val wineGradeRepository: WineGradeRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.Effect>(
    initialState = MyPageContract.State()
) {

    override fun reduceState(event: MyPageContract.Event) {
        viewModelScope.launch {
            when (event) {
                is MyPageContract.Event.SelectReason -> {
                    postEffect(
                        MyPageContract.Effect.ShowBottomSheet(
                            MyPageContract.BottomSheet.SelectWithdrawalReason
                        )
                    )
                }

                is MyPageContract.Event.CompleteWithdrawal -> {
                    postEffect(
                        MyPageContract.Effect.ShowBottomSheet(
                            MyPageContract.BottomSheet.WithdrawalComplete
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
}