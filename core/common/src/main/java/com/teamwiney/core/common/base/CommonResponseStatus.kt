package com.teamwiney.core.common.base


/**
 * 에러 코드 관리
 */
enum class CommonResponseStatus(val code: String, val message: String) {

    /**
     * 잘못된 요청
     */
    INTERNAL_SERVER_ERROR(
        "COMMON000",
        "서버 에러, 관리자에게 문의 바랍니다."
    ),
    BAD_REQUEST(
        "COMMON001", "잘못된 요청입니다."
    ),
    UNAUTHORIZED(
        "COMMON002",
        "권한이 잘못되었습니다"
    ),
    METHOD_NOT_ALLOWED(
        "COMMON003", "지원하지 않는 Http Method 입니다."
    ),

    /**
     * 인증 관련 에러코드
     */
    FORBIDDEN_EXCEPTION(
        "AUTH002", "해당 요청에 대한 권한이 없습니다."
    ),
    UNAUTHORIZED_EXCEPTION(
        "AUTH003",
        "로그인 후 이용가능합니다. 토큰을 입력해 주세요"
    ),
    EXPIRED_JWT_EXCEPTION(
        "AUTH004",
        "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."
    ),
    RELOGIN_EXCEPTION(
        "AUTH005",
        "모든 토큰이 만료되었습니다. 다시 로그인해주세요."
    ),
    INVALID_TOKEN_EXCEPTION(
        "AUTH006", "토큰이 올바르지 않습니다."
    ),
    HIJACK_JWT_TOKEN_EXCEPTION(
        "AUTH007",
        "탈취된(로그아웃 된) 토큰입니다 다시 로그인 해주세요."
    ),
    INVALID_REFRESH_TOKEN(
        "AUTH009", "리프레쉬 토큰이 유효하지 않습니다. 다시 로그인 해주세요"
    ),

    /**
     * 1000 : 소셜 관련 예외 처리
     */
    APPLE_BAD_REQUEST(
        "OAUTH001", "애플 토큰이 잘못되었습니다."
    ),
    APPLE_SERVER_ERROR(
        "OAUTH002",
        "애플 서버와 통신에 실패 하였습니다."
    ),
    FAIL_TO_MAKE_APPLE_PUBLIC_KEY(
        "OAUTH003", "새로운 애플 공개키 생성에 실패하였습니다."
    ),

    /**
     * UXXX : USER 관련 에러
     */
    USER_STATUS_UNACTIVATED(
        "U000",
        "유저가 비활성화 상태입니다."
    ),
    USERS_EMPTY_USER_ID(
        "U001", "유저 아이디 값을 입력해주세요."
    ),
    USERS_EMPTY_USER_PASSWORD(
        "U002",
        "유저 비밀번호를 입력해주세요."
    ),
    TOO_SHORT_PASSWORD(
        "U003",
        "비밀번호의 길이를 8자 이상을 설정해주세요."
    ),
    FAILED_TO_SIGN_UP(
        "U004", "회원가입에 실패하였습니다."
    ),
    USERS_EXISTS_ID(
        "U005",
        "중복된 전화번호입니다."
    ),
    USERS_EXISTS_NICKNAME(
        "U006", "중복된 닉네임입니다."
    ),
    POST_USERS_EMPTY_NICKNAME(
        "U007",
        "닉네임을 입력해주세요."
    ),
    FAILED_TO_LOGIN(
        "U008", "로그인에 실패하였습니다."
    ),
    NOT_EXIST_USER(
        "U009",
        "해당 유저가 존재하지 않습니다."
    ),
    USER_ALREADY_EXISTS(
        "U010", "이미 가입된 유저입니다."
    ),

    /**
     * MXXX : Message 관련 에러
     */
    MESSAGE_SEND_FAILED(
        "M001", "메시지 전송이 실패했습니다. 올바른 번호인지 확인하세요."
    ),
    MESSAGE_NOT_FOUND(
        "M002",
        "인증번호 전송 기록이 존재하지 않습니다."
    ),
    MESSAGE_VERIFICATION_TIMEOUT(
        "M003",
        "인증 번호가 만료되었습니다."
    ),
    VERIFICATION_DID_NOT_MATCH(
        "M004", "인증 번호가 일치하지 않습니다."
    ),
    MESSAGE_SEND_TOO_MANY_ATTEMPTS(
        "M005", "인증번호 요청 횟수가 초과되었습니다."
    )

}