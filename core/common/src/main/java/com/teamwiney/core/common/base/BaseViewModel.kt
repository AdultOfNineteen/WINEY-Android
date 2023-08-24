package com.teamwiney.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiState
interface UiEvent
interface UiEffect
interface UiSheet

/**
 * MVI 패턴을 따르기 위한 ViewModel 추상 클래스
 * UI State, event, side effect를 표준화된 패턴에 따라 관리
 *
 * @param State UI 상태 (로딩 상태, 화면에 표시할 데이터 등)
 * @param Event UI에서 발생하는 동작이나 이벤트 (버튼 클릭, 아이템 선택 이벤트 등)
 * @param Effect 비동기 작업 및 UI 이벤트 외의 작업 (스낵 바, 화면 전환, 데이터베이스 작업 등)
 * @property initialState UI의 초기 상태
 */
abstract class BaseViewModel<State: UiState, Event: UiEvent, Effect: UiEffect>(
    initialState: State
) : ViewModel() {
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val currentState: State
        get() = _uiState.value

    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    protected abstract fun reduceState(event: Event)
    protected suspend fun postEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                reduceState(it)
            }
        }
    }
    protected fun updateState(currentState: State) {
        _uiState.value = currentState
    }
    // TODO : processEvent 메소드 사용할지 고민해보기
    // onClick = { processEvent(LoginContract.Event.KaKaoButtonClicked()) } 처럼 사용?
    // 아니면 ViewModel 단의 kakaoLogin 메소드에서 _event.emit()하고
    // onClick = { kakaoLogin() } 이런 식으로 사용?
    fun processEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }
}