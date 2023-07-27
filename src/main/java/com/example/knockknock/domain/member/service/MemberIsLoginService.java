package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberIsLoginService {

    @Transactional
    public Member isLogin(UserDetailsImpl userDetails){
        if(userDetails != null){
            return userDetails.getUser();
        } else{
            throw new GlobalException(GlobalErrorCode.LOGIN_REQUIRED);
        }
    }
}
