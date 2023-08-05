package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.dto.request.EmailAuthenticationRequestDto;
import com.example.knockknock.domain.member.dto.request.LoginRequestDto;
import com.example.knockknock.domain.member.dto.request.MemberSignUpRequestDto;
import com.example.knockknock.domain.member.dto.request.MemberUpdateRequestDto;
import com.example.knockknock.domain.member.dto.response.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.response.MemberDetailResponseDto;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.global.email.EmailService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import com.example.knockknock.global.image.service.S3Service;
import com.example.knockknock.global.jwt.JwtUtil;
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
    private final EmailAuthenticationService emailAuthenticationService;


    // 회원가입
    @Transactional
    public void signup(final MemberSignUpRequestDto signupRequestDto, MultipartFile profileImage) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 중복 아이디 불가
        Optional<Member> found = memberRepository.findByEmail(email);
        if(found.isPresent()){
            throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
        }

        createMember(signupRequestDto, profileImage, password);
    }


    @Transactional
    public void createMember(MemberSignUpRequestDto request, MultipartFile profileImage, String password) {
        Member member = Member.builder()
                .memberName(request.getMemberName())
                .memberGender(request.getMemberGender())
                .nickName(request.getNickName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .password(password)
                .build();
        if(profileImage != null) {
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
    @Transactional
    public void authentication(EmailAuthenticationRequestDto request){
        String email = request.getEmail();
        // 사용자의 이메일 주소로 Member를 찾음 (이메일 주소가 일치해야 함)
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        String code = emailAuthenticationService.generateCode();

        String emailBody = "이메일을 인증하려면 아래 코드를 입력하세요:\n" + code;
        emailService.sendEmail(email, "메일 테스트", emailBody);
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
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member));
    }


    @Transactional
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        return MemberDetailResponseDto.of(member);
    }

    @Transactional
    public List<GetMembersResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(GetMembersResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto request, MultipartFile profileImage) {
        Member member = memberRepository.findById(memberId)
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

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }

    public void test() {
        log.info("123123123");
    }
}
