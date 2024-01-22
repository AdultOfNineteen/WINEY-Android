package com.teamwiney.data.network.model.response

import com.teamwiney.core.common.model.UserStatus

/**
 * - userStatus
1. 취향설정 까지 모두 마쳐 회원 가입이 완료된 경우 → `ACTIVE`
2. 가입이 완료되지 않은 경우 → `INACTIVE`

- messageStatus
1. 문자 전송 기록이 있는 경우
- 전송은 했으나 인증 번호 입력을 하지 않은 경우 → `PENDING`
- 인증이 완료된 경우 → `VERIFIED`
- 인증이 실패한 경우 → `FAILED`
2. 문자 전송 기록이 없는 경우
- `NONE`

- preferenceStatus
1. 취향 설정을 한 경우 → `DONE`
2. 취향 설정을 하지 않은 경우 → `NONE`
 */
data class SocialLogin(
    val accessToken: String,
    val userId: Int,
    val refreshToken: String,
    val userStatus: UserStatus,
    val messageStatus: String,
    val preferenceStatus: String
)