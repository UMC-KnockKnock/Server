package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.dto.request.*;
import com.example.knockknock.domain.member.dto.response.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.response.MemberDetailResponseDto;
import com.example.knockknock.domain.member.entity.EmailCode;

import com.example.knockknock.domain.member.repository.EmailCodeRepository;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.RefreshTokenRepository;
import com.example.knockknock.domain.member.security.RefreshToken;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.email.EmailService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import com.example.knockknock.global.image.service.S3Service;
import com.example.knockknock.global.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final EmailService emailService;
    private final MemberIsLoginService memberIsLoginService;
    private final EmailAuthenticationService emailAuthenticationService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final EmailCodeRepository emailCodeRepository;

    //닉네임 중복검사
    @Transactional
    public void checkNickname (NicknameCheckRequestDto request){
        Optional<Member> found = memberRepository.findByNickName(request.getNickName());
        if(found.isPresent()){
            throw new GlobalException(GlobalErrorCode.DUPLICATE_NICK_NAME);
        }
    }
    //이메일 중복검사
    @Transactional
    public void checkEmail(String email){
        Optional<Member> found = memberRepository.findByEmail(email);
        if(found.isPresent()){
            throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
        }
    }


    // 회원가입
    @Transactional
    public void signup(final MemberSignUpRequestDto signupRequestDto, MultipartFile profileImage) {
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        createMember(signupRequestDto, profileImage, password);
    }

    //멤버 객체 생성
    @Transactional
    public void createMember(MemberSignUpRequestDto request, MultipartFile profileImage, String password) {
        Member member = Member.builder()
                .memberGender(request.getMemberGender())
                .nickName(request.getNickName())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .password(password)
                .build();
        if(profileImage != null && !profileImage.isEmpty()) {
            String imageUrl = null;
            try {
                imageUrl = s3Service.uploadImage(profileImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } member.setProfileImageURL(imageUrl);

        } else member.setProfileImageURL("https://e7.pngegg.com/pngimages/195/830/png-clipart-emoji-silhouette-service-company-person-emoji-cdr-head.png");
        member.calculateAge();
        memberRepository.save(member);
    }

    //이메일 인증 코드 발송
    @Transactional
    public void sendCode(EmailAuthenticationRequestDto request){
        String email = request.getEmail();
        checkEmail(email);
        Optional<EmailCode> codeOptional = emailCodeRepository.findByEmail(email);
        if(codeOptional.isPresent()){
            EmailCode code = codeOptional.get();
            emailCodeRepository.delete(code);
        } else {
        String code = emailAuthenticationService.generateCode();

        EmailCode emailCode = EmailCode.builder()
                .email(email)
                .code(code)
                .build();

        emailCodeRepository.save(emailCode);

        String emailBody = "이메일을 인증하려면 아래 코드를 입력하세요:\n" + code;
        emailService.sendEmail(email, "KnockKnock 이메일 인증 코드", emailBody);
        }
    }

    //이메일 인증코드 확인
    @Transactional
    public Boolean isValid(CheckAuthCodeRequestDto request) {
        String email = request.getEmail();
        String code = request.getCode();
        Optional<EmailCode> codeOptional = emailCodeRepository.findByEmailAndCode(email, code);
        if (codeOptional.isPresent()){
            EmailCode emailCode = codeOptional.get();
            emailCodeRepository.delete(emailCode);
            return true;
        } else {
            return false;
        }
    }

    // 유저 로그인
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND)
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new GlobalException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        // http 응답에 헤더 추가
        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, jwtUtil.createAccessToken(email));
        String rawToken = jwtUtil.createRefreshToken(email);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, rawToken); // 리프레시 토큰 추가
        RefreshToken refreshToken = new RefreshToken(rawToken.substring(7), email);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");
        boolean isValidRefreshToken = jwtUtil.validateRefreshToken(refreshToken);
        if(isValidRefreshToken){
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
        } else {
            throw new GlobalException(GlobalErrorCode.INVALID_TOKEN);
        }
    }

    //토큰 재발급
    @Transactional
    public String reissue(HttpServletRequest request){
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");
        if (refreshToken == null) {
            log.warn("Refresh token not found in the request");
            return null;
        }

        boolean isValidRefreshToken = jwtUtil.validateRefreshToken(refreshToken);
        if (!isValidRefreshToken) {
            log.warn("Invalid refresh token: {}", refreshToken);
            throw new GlobalException(GlobalErrorCode.INVALID_TOKEN);
        } else {
            String memberEmail = jwtUtil.getMemberEmailFromToken(refreshToken);
            String newAccessToken = jwtUtil.createAccessToken(memberEmail);

            return newAccessToken;
        }
    }

    //내 정보 보기
    @Transactional
    public MemberDetailResponseDto getMemberDetail(UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);

        return MemberDetailResponseDto.of(member);
    }
    //가입된 모든 회원 목록
    @Transactional
    public List<GetMembersResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(GetMembersResponseDto::from)
                .collect(Collectors.toList());
    }

    //내 정보 수정
    @Transactional
    public void updateMember(UserDetailsImpl userDetails, MemberUpdateRequestDto request, MultipartFile profileImage) {
        Member targetMember = memberIsLoginService.isLogin(userDetails);
        Member member = memberRepository.findById(targetMember.getMemberId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        if(profileImage != null) {
            String imageUrl = null;
            try {
                imageUrl = s3Service.uploadImage(profileImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } member.setProfileImageURL(imageUrl);
        }
        member.updateMember(request);
    }
    //회원 탈퇴
    @Transactional
    public void deleteMember(UserDetailsImpl userDetails) {
        Member targetMember = memberIsLoginService.isLogin(userDetails);
        Member member = memberRepository.findById(targetMember.getMemberId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }

    public void test() {
        log.info("123123123");
    }
}
