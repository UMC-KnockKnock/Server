package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.dto.request.CheckResetCodeRequestDto;
import com.example.knockknock.domain.member.dto.request.EmailAuthenticationRequestDto;
import com.example.knockknock.domain.member.dto.request.PasswordUpdateRequestDto;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.entity.PasswordResetCode;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.repository.PasswordResetCodeRepository;
import com.example.knockknock.global.email.EmailService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetCodeRepository resetCodeRepository;
    private final MemberRepository memberRepository;

    private final EmailService emailService;
    private final EmailAuthenticationService emailAuthenticationService;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void sendResetCode(EmailAuthenticationRequestDto request) {
        String email = request.getEmail();
        // 사용자의 이메일 주소로 Member를 찾음 (이메일 주소가 일치해야 함)
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        Optional<PasswordResetCode> codeOptional = resetCodeRepository.findByMember(member);
        if(codeOptional.isPresent()){
            PasswordResetCode code = codeOptional.get();
            resetCodeRepository.delete(code);
        } else {
        String code = emailAuthenticationService.generateCode();

        // 비밀번호 재설정 코드 생성
        PasswordResetCode resetCode = PasswordResetCode.builder()
                .member(member)
                .code(code)
                .build();

        resetCodeRepository.save(resetCode);

        // 이메일로 비밀번호 재설정 코드를 발송
        String emailBody = "비밀번호를 재설정하려면 아래 코드를 입력하세요:\n" + code;
        emailService.sendEmail(email, "KnockKnock 비밀번호 변경 인증 코드", emailBody);
        }
    }

    @Transactional
    public Long isAuthenticated(CheckResetCodeRequestDto request){
        PasswordResetCode resetCode = resetCodeRepository.findByCode(request.getCode())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.INVALID_CODE));
        return resetCode.getMember().getMemberId();
    }
    @Transactional
    public void resetPassword(PasswordUpdateRequestDto request){
            Member member = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
            PasswordResetCode passwordResetCode = resetCodeRepository.findByMember(member)
                        .orElseThrow(() -> new GlobalException(GlobalErrorCode.AUTHENTICATION_REQUIRED));
            String encryptedPassword = passwordEncoder.encode(request.getNewPassword());
            member.setPassword(encryptedPassword);
            memberRepository.save(member);
            resetCodeRepository.delete(passwordResetCode);
    }

}
