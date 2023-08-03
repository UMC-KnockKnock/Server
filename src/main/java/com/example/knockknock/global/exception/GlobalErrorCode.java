package com.example.knockknock.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
    //  Member
    // 400 BAD_REQUEST - 잘못된 요청
    NOT_VALID_EMAIL(BAD_REQUEST, "유효하지 않은 이메일 입니다."),
    PASSWORD_MISMATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_PASSWORD(BAD_REQUEST, "비밀번호가 동일합니다."),
    DUPLICATE_NICK_NAME(BAD_REQUEST, "닉네임이 동일합니다."),
    NOT_VALID_NICKNAME(BAD_REQUEST, "영문, 한글, 숫자를 포함한 2~ 16글자를 입력해 주세요."),
    NOT_VALID_PASSWORD(BAD_REQUEST, "영문, 숫자, 특수문자를 포함한 8~20 글자를 입력해 주세요."),
    NOT_VALID_PHONENUMBER(BAD_REQUEST, "11자리 이내의 번호를 '-'를 제외한 숫자만 입력해 주세요."),
    // 401 Unauthorized - 권한 없음
    INVALID_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다"),
    LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    // 404 Not Found - 찾을 수 없음
    EMAIL_NOT_FOUND(NOT_FOUND, "존재하지 않는 이메일 입니다."),
    NEED_AGREE_REQUIRE_TERMS(NOT_FOUND, "필수 약관에 동의해 주세요"),
    USER_NOT_FOUND(NOT_FOUND, "등록된 사용자가 없습니다"),
    USERINFO_NOT_FOUND(NOT_FOUND, "등록된 사용자 정보가 없습니다"),
    // 409 CONFLICT : Resource 를 찾을 수 없음
    DUPLICATE_EMAIL(CONFLICT, "중복된 이메일이 존재합니다"),
    EXPIRED_REFRESH_TOKEN(BAD_REQUEST, "쿠키에 토큰이 없습니다."),

    // Friend
    FRIEND_NOT_FOUND(NOT_FOUND, "등록된 친구가 없습니다"),

    // Notification
    NOTIFICATION_NOT_FOUND(NOT_FOUND, "등록된 연락 일정이 없습니다"),

    //Board
    // 400 BAD_REQUEST - 잘못된 요청
    DUPLICATE_REPORT(BAD_REQUEST, "이미 신고한 게시물/댓글입니다."),
    // 401 Unauthorized = 권한 없음
    PERMISSION_DENIED(UNAUTHORIZED, "권한이 없습니다."),
    // 404 Not Found - 찾을 수 없음
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    POST_NOT_FOUND(NOT_FOUND, "게시글이 존재하지 않습니다"),
    COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 댓글입니다."),
    REPLY_NOT_FOUND(NOT_FOUND, "존재하지 않는 답글입니다."),
    HASHTAG_NOT_FOUND(NOT_FOUND, "존재하지 않는 해시태그입니다."),

    //Gathering
    //404 Not Found - 찾을 수 없음
    GATHERING_NOT_FOUND(NOT_FOUND, "존재하지 않는 모임입니다."),

    ;




    private final HttpStatus httpStatus;
    private final String message;
}
