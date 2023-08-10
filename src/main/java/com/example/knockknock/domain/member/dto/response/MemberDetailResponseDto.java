package com.example.knockknock.domain.member.dto.response;

import com.example.knockknock.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailResponseDto {
    private Long memberId;
    private String memberGender;
    private String profileImageUrl;
    private String nickName;
    private String email;
    private String birthday;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static MemberDetailResponseDto of(Member member) {
//        String formattedBirthday = null;
//        if (member.getBirthday() != null) {
//            formattedBirthday = formatDate(member.getBirthday());
//        }
        return MemberDetailResponseDto.builder()
                .memberId(member.getMemberId())
                .memberGender(String.valueOf(member.getMemberGender()))
                .profileImageUrl(member.getProfileImageURL())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .age(member.getAge())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();

    }
//    private static String formatDate(String birthday) {
//        LocalDate currentDate = LocalDate.now();
//        int birthYear = Integer.parseInt(birthday.substring(0, 2));// YYMMDD에서 YY 추출
//        int birthMonth = Integer.parseInt(birthday.substring(2, 4));  // YYMMDD에서 MM 추출
//        int birthDate = Integer.parseInt(birthday.substring(4, 6));  // YYMMDD에서 DD 추출
//
//        int currentYear = currentDate.getYear();  // 현재 년도의 YY 추출
//
//        if ((currentYear % 100 < birthYear) && (birthYear < 100)){
//            birthYear += 1900; // 2자리 연도를 4자리로 확장
//        }
//        if ((currentYear % 100 >= birthYear) && (birthYear < 100)) {
//            birthYear += 2000;
//        }
//        String year = Integer.toString(birthYear);
//        String month = String.format("%02d", birthMonth);
//        String day = String.format("%02d", birthDate);
//        return year + "년 " + month + "월 " + day + "일";
//    }
}
