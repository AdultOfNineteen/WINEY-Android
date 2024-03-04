package com.teamwiney.auth.signup

import androidx.lifecycle.viewModelScope
import com.teamwiney.auth.signup.component.state.SignUpFavoriteCategoryUiState
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.base.CommonResponseStatus
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.util.Constants
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<SignUpContract.State, SignUpContract.Event, SignUpContract.Effect>(
    initialState = SignUpContract.State()
) {

    override fun reduceState(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.SendAuthentication -> {
                sendAuthenticationNumber()
            }

            is SignUpContract.Event.BackToLogin, SignUpContract.Event.CancelTasteSelection -> {
                postEffect(
                    SignUpContract.Effect.ShowBottomSheet(
                        SignUpContract.BottomSheet.ReturnToLogin
                    )
                )
            }

            is SignUpContract.Event.SetPreferences -> {
                setPreferences()
            }

            is SignUpContract.Event.VerifyCode -> {
                verifyAuthenticationNumber()
            }
        }
    }

    private fun setPreferences() = viewModelScope.launch {
        authRepository.setPreferences(
            userId = currentState.userId,
            request = SetPreferencesRequest(
                chocolate = currentState.favoriteTastes[0].signUpFavoriteItem.find { it.isSelected }?.keyword!!,
                coffee = currentState.favoriteTastes[1].signUpFavoriteItem.find { it.isSelected }?.keyword!!,
                fruit = currentState.favoriteTastes[2].signUpFavoriteItem.find { it.isSelected }?.keyword!!,
            )
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    registerFcmToken()  // 회원가입 완료 후 FCM 토큰 등록
                    postEffect(SignUpContract.Effect.NavigateTo(AuthDestinations.SignUp.COMPLETE))
                }

                is ApiResult.ApiError -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun verifyAuthenticationNumber() = viewModelScope.launch {
        authRepository.verifyAuthCodeMessage(
            currentState.userId,
            PhoneNumberWithVerificationCodeRequest(
                phoneNumber = currentState.phoneNumber,
                verificationCode = currentState.verifyNumber
            )
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    postEffect(SignUpContract.Effect.NavigateTo(AuthDestinations.SignUp.FAVORITE_TASTE))
                }

                is ApiResult.ApiError -> {
                    updateState(currentState.copy(
                        verifyNumberErrorText = "인증번호를 확인해주세요!",
                        verifyNumberErrorState = true
                    ))
                }

                else -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun sendAuthenticationNumber() = viewModelScope.launch {
        authRepository.sendAuthCodeMessage(
            currentState.userId,
            PhoneNumberRequest(
                currentState.phoneNumber
            )
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    resetTimer()
                    postEffect(
                        SignUpContract.Effect.ShowBottomSheet(
                            SignUpContract.BottomSheet.SendMessage
                        )
                    )

                    val sendCount = currentState.sendCount + 1
                    if (sendCount > 3) {
                        postEffect(
                            SignUpContract.Effect.ShowBottomSheet(
                                SignUpContract.BottomSheet.SendTimeExceededLimit
                            )
                        )
                    }
                    updateState(currentState.copy(sendCount = sendCount))
                }

                is ApiResult.ApiError -> {
                    when (it.code) {
                        CommonResponseStatus.USER_ALREADY_EXISTS.code -> {
                            postEffect(
                                SignUpContract.Effect.ShowBottomSheet(
                                    SignUpContract.BottomSheet.UserAlreadyExists(
                                        it.message
                                    )
                                )
                            )
                        }
                        CommonResponseStatus.MESSAGE_SEND_TOO_MANY_ATTEMPTS.code -> {
                            postEffect(
                                SignUpContract.Effect.ShowBottomSheet(
                                    SignUpContract.BottomSheet.SendDisabled
                                )
                            )
                        }
                        else -> {
                            postEffect(SignUpContract.Effect.ShowSnackBar(it.message))
                        }
                    }
                }

                else -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun registerFcmToken() = viewModelScope.launch {
        val fcmToken = dataStoreRepository.getStringValue(Constants.FCM_TOKEN).first()
        val deviceId = dataStoreRepository.getStringValue(Constants.DEVICE_ID).first()

        authRepository.registerFcmToken(fcmToken, deviceId).collectLatest {
            when (it) {
                is ApiResult.ApiError -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }

                else -> { }
            }
        }
    }

    fun updateUserId(userId: String) = viewModelScope.launch {
        updateState(currentState.copy(userId = userId))
    }

    fun updatePhoneNumber(phoneNumber: String) = viewModelScope.launch {
        updateState(currentState.copy(phoneNumber = phoneNumber))
    }

    fun updatePhoneNumberErrorState(isError: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(phoneNumberErrorState = isError))
    }

    fun updateVerifyNumber(verifyNumber: String) = viewModelScope.launch {
        updateState(currentState.copy(verifyNumber = verifyNumber))
        if (currentState.verifyNumber.length == 6 || currentState.verifyNumber.isEmpty()) {
            updateState(currentState.copy(
                verifyNumberErrorText = "인증번호",
                verifyNumberErrorState = false
            ))
        } else {
            updateState(currentState.copy(
                verifyNumberErrorText = "인증번호 ${SignUpContract.VERIFY_NUMBER_LENGTH}자리를 입력해주세요",
                verifyNumberErrorState = true
            ))
        }
    }

    fun updateRemainingTime(remainingTime: Int) = viewModelScope.launch {
        updateState(currentState.copy(remainingTime = remainingTime))
    }

    fun updateIsTimerRunning(isTimerRunning: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(isTimerRunning = isTimerRunning))
    }

    fun updateIsTimeOut(isTimeOut: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(isTimeOut = isTimeOut))
        if (isTimeOut) {
            postEffect(
                SignUpContract.Effect.ShowBottomSheet(
                    SignUpContract.BottomSheet.AuthenticationTimeOut
                )
            )
        }
    }

    fun resetTimer() = viewModelScope.launch {
        updateState(
            currentState.copy(
                remainingTime = SignUpContract.VERIFY_NUMBER_TIMER,
                isTimerRunning = true,
                isTimeOut = false,
                isLoading = false
            )
        )
    }

    fun updateSignUpFavoriteItem(signUpFavoriteCategoryUiState: SignUpFavoriteCategoryUiState) =
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    favoriteTastes = currentState.favoriteTastes.map {
                        if (it.title == signUpFavoriteCategoryUiState.title) {
                            signUpFavoriteCategoryUiState
                        } else {
                            it
                        }
                    }
                )
            )
        }
}

